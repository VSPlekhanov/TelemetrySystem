FROM node:18 as build
WORKDIR /app
COPY package*.json ./
RUN npm install
#COPY ../../backend .
RUN npm run build

FROM openjdk:11
COPY agent.jar .
COPY web.jar .
COPY .env .
EXPOSE 8080
EXPOSE 3000

FROM nginx
#COPY ./nginx/defauld.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/build /app/static-files