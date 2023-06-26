FROM node:20.2-alpine3.16

COPY app/static /app/static
COPY app/index.js /app/index.js
COPY app/flag.txt /app/flag.txt
COPY app/package.json /app/package.json
COPY app/package-lock.json /app/package-lock.json

WORKDIR /app

RUN npm install

EXPOSE 80
CMD ["node", "index.js"]