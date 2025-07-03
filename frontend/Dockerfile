FROM webratio/nodejs-http-server

VOLUME /tmp
ADD dist /opt/www
ADD run.sh /opt/run.sh
ARG VITE_API_BASE_URL
ENV VITE_API_BASE_URL=$VITE_API_BASE_URL
# ARG VUE_APP_API_HOST
# ENV VUE_APP_API_HOST=$VUE_APP_API_HOST
EXPOSE 8080
ENTRYPOINT ["sh","/opt/run.sh" ]
