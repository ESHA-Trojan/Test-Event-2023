FROM alpine:latest AS app
RUN apk add --no-cache gcc musl-dev

WORKDIR /app

COPY socrates_flag.txt /app/socrates_flag.txt
COPY socrates.c /app/socrates.c
RUN gcc -o run socrates.c
RUN chmod 755 /app/run

# Add OS
FROM pwn.red/jail
COPY --from=app / /srv

ENV JAIL_MEM=20M JAIL_TIME=0