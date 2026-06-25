FROM nginx:alpine

# Copiar archivos del frontend al directorio de nginx
COPY . /usr/share/nginx/html/

# Exponer puerto 4200
EXPOSE 4200

# Crear configuración personalizada de nginx para puerto 4200
RUN echo 'server { \
    listen 4200; \
    server_name localhost; \
    root /usr/share/nginx/html; \
    index index.html; \
    location / { \
        try_files $uri $uri/ /index.html; \
    } \
}' > /etc/nginx/conf.d/default.conf

CMD ["nginx", "-g", "daemon off;"]
