FROM node:18 as build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM openjdk:11
COPY ../../backend/web/build/libs/web-1.0-SNAPSHOT.jar web.jar
COPY ../../.env .
EXPOSE 3000

#FROM nginx
#COPY ./nginx/defauld.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/build /app/static-files