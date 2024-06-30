FROM node:20

WORKDIR /app
COPY ./dist ./dist
COPY ./package.json .
COPY ./package-lock.json .
COPY ./server.js .

RUN npm install
EXPOSE 3001

CMD ["node", "server.js"]