#!/usr/bin/env python

import sys
import imaplib
import getpass
import email
import datetime

def process_mailbox(M, subject):
  # rv, data = M.search(None, "ALL")
  rv, data = M.search(None, '(SUBJECT "'+subject+'")')
  # rv, data = M.search(None, '(FROM "pallat")')
  if rv != 'OK':
      print "No messages found!"
      return

  for num in data[0].split():
      rv, data = M.fetch(num, '(RFC822)')
      if rv != 'OK':
          print "ERROR getting message", num
          return

      msg = email.message_from_string(data[0][1])
      print 'From : %s' % (msg['From'])
      return (msg['From'])

def get_sender_name(account,password,subject):
  M = imaplib.IMAP4_SSL('imap.gmail.com')

  try:
      M.login(account, password)
  except imaplib.IMAP4.error:
      print "LOGIN FAILED!!! "

  rv, data = M.select("[Gmail]/Sent Mail")
  if rv == 'OK':
      result = process_mailbox(M,subject)
      M.close()
  M.logout()
  return result


from robot.api import logger
def write_to_console(s):
    logger.console(s)