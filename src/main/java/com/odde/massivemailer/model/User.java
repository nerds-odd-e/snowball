package com.odde.massivemailer.model;

import com.odde.massivemailer.controller.config.ApplicationConfiguration;
import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.base.Entity;
import com.odde.massivemailer.service.MailService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.math.BigInteger;
import java.security.MessageDigest;

import static com.mongodb.client.model.Filters.eq;
import static com.odde.massivemailer.model.base.Repository.repo;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User extends Entity<User> {
    private String name;
    private String email;
    private String hashdPassword;
    private String token;

    public User(String email) {
        setEmail(email);
        setToken(createToken());
    }

    private static String createToken() {
        return RandomStringUtils.randomAlphanumeric(100);
    }

    public static void createUnconfirmedUser(String email1, MailService mailService) throws EmailException {
        User user = new User(email1);
        user.save();
        Mail email = new Mail();
        email.setSubject("");
        email.setContent(new ApplicationConfiguration().getRoot() + "initialPassword?token=" + user.getToken());
        email.sendMailToRecipient(email1, mailService);
    }

    public void setPassword(String password) {
        setHashdPassword(toHashString(password));
    }

    public static User getUserByEmail(String email) {
        return repo(User.class).findFirstBy("email", email);
    }

    public boolean isPasswordCorrect(String password) {
        String userPassword = getHashdPassword();
        if (userPassword == null) {
            return false;
        }

        String hashedPassword = toHashString(password);
        return userPassword.equals(hashedPassword);
    }

    private String toHashString(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] result = digest.digest(password.getBytes());
            return String.format("%040x", new BigInteger(1, result));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static User fetchUserByToken(String token) {
        if (null == token) {
            return null;
        }
        return repo(User.class).findFirstBy("token", token);
    }

    public static boolean validatePassword(String password) {
        return !StringUtils.isEmpty(password);
    }

    @Override
    public void onBeforeSave() {
    }

    public static class UserCodec implements Codec<User> {
        @Override
        public void encode(final BsonWriter writer, final User value, final EncoderContext encoderContext) {
            writer.writeStartDocument();
            writer.writeObjectId("_id", value.id);
            writer.writeName("name");
            writer.writeString(defaultIfEmpty(value.name, ""));
            writer.writeName("email");
            writer.writeString(defaultIfEmpty(value.email, ""));
            writer.writeName("hashdPassword");
            writer.writeString(defaultIfEmpty(value.hashdPassword, ""));
            writer.writeName("token");
            writer.writeString(defaultIfEmpty(value.token, ""));
            writer.writeEndDocument();
        }

        @Override
        public User decode(final BsonReader reader, final DecoderContext decoderContext) {
            User user = new User();
            reader.readStartDocument();
            user.id = reader.readObjectId("_id");
            reader.readName();
            user.name = reader.readString();
            reader.readName();
            user.email = reader.readString();
            reader.readName();
            user.hashdPassword = reader.readString();
            reader.readName();
            user.token = reader.readString();
            reader.readEndDocument();
            return user;
        }

        @Override
        public Class<User> getEncoderClass() {
            return User.class;
        }
    }

}
