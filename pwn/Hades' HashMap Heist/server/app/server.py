#!/usr/local/bin/python3

import socket
import subprocess
import sys

HOST = sys.argv[1]
PORT = int(sys.argv[2])
COMMAND = sys.argv[3].split(" ")

def handle_connection(conn, sock):
	print(f"<< Accepting incoming connection from {conn}")

	# Make IO files
	f_out = sock.makefile('wb', 0)
	f_in = sock.makefile('rb', 0)

	# Start subprocess, route input to command, route output (STDOUT & STDERR) to command
	subprocess.run(COMMAND, stdin=f_in, stdout=f_out, stderr=subprocess.STDOUT)

	# Close socket
	sock.close()

print("Starting server.py")

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server:
	# Bind socket
	server.bind((HOST, PORT))
	server.listen() # TODO set backlog?

	print("TCP Server ready to accept connections")

	while True:
		# Accept and handle connection
		sock, conn = server.accept()
		handle_connection(conn, sock)
