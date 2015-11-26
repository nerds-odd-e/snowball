package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.model.Template;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Cadet on 11/26/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class SqliteTemplateTest {

    private SqliteTemplate sqliteTemplate;

    @Mock
    private Statement mockStatement;

    @Mock
    private Connection mockConnection;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        sqliteTemplate = new SqliteTemplate();
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(null);
        sqliteTemplate.setConnection(mockConnection);
        // for addContact test

    }


    @After
    public void tearDown() {

    }

    @Test
    public void testGetTemplateList() throws SQLException{

        // Arrange
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getInt("Id")).thenReturn(1);
        when(resultSetMock.getString("TemplateName")).thenReturn("GreetingTemplate");
        when(resultSetMock.getString("Subject")).thenReturn("template subject {Company}");
        when(resultSetMock.getString("Content")).thenReturn("template content {FirstName}");
        when(mockStatement.executeQuery(anyString())).thenReturn(resultSetMock);

        // Act
        List<Template> templateList = sqliteTemplate.getTemplateList();

        // Assert

        assertEquals(1, templateList.size());
        Template template = templateList.get(0);
        assertEquals(1, template.getId());
        assertEquals("GreetingTemplate", template.getTemplateName());
        assertEquals("template subject {Company}", template.getSubject());
        assertEquals("template content {FirstName}", template.getContent());

        verify(mockStatement).executeQuery(anyString());

    }

}
