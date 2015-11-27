import sqlite3


def clear_all_contacts():
	conn = sqlite3.connect('/usr/share/oddemail.db')
	c = conn.cursor()
	c.execute('''delete from mail''')
	conn.commit()
	conn.close()
