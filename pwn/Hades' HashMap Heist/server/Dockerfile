FROM debian:bullseye-slim
RUN apt update; apt install -y python3 openjdk-17-jre-headless

COPY app /app/
WORKDIR /app/

ENTRYPOINT [ "python3", "server.py", "0.0.0.0", "5000", "java Charon" ]
