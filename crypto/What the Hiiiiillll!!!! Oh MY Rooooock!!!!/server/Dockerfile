FROM python:3.11-slim-bullseye AS app
# Place any pip install cmds here
RUN pip install --no-cache-dir numpy

# Add OS
FROM pwn.red/jail
COPY --from=app / /srv

COPY flag.txt /srv/app/flag.txt
# Copy script, requires shebang in script
COPY hill.py /srv/app/run
RUN chmod 755 /srv/app/run

ENV JAIL_MEM=20M JAIL_TIME=0