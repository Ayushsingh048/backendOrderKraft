# Stage 1: Build Angular app
FROM node:18 AS build
WORKDIR /app
COPY orderkraft-frontend/ ./   # copy Angular project
RUN npm install -g @angular/cli
RUN npm install
RUN ng build --configuration production

# Stage 2: Serve with Nginx
FROM nginx:alpine
COPY --from=build /app/dist/ /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
