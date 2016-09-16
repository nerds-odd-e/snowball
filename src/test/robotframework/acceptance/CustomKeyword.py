#import sqlite3


def clear_all_contacts():
	conn = sqlite3.connect('/usr/share/oddemail.db')
	c = conn.cursor()
	c.execute('''delete from mail''')
	conn.commit()
	conn.close()

def insert_default_template():
	pass

def clear_template():
	conn = sqlite3.connect('..\..\..\..\oddemail.db')
	c = conn.cursor()
	c.execute("DELETE FROM Template")
	conn.commit()
	conn.close()