FROM nginx:1.27-alpine
# Copy site content
COPY . /usr/share/nginx/html
# Replace default server config
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
