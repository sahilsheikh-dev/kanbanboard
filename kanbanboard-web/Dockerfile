# node block
FROM node:18.14.0-alpine as nodework
WORKDIR /app
COPY ./package.json .
RUN npm install
COPY . .
ARG API_BASE_URL
ENV API_BASE_URL=${API_BASE_URL}
RUN npm run build

# nginx block
FROM nginx:1.25.2-alpine
WORKDIR /usr/share/nginx/html
RUN rm -rf ./*
COPY --from=nodework /app/build .
EXPOSE 80
ENTRYPOINT ["nginx","-g","daemon off;"]