FROM debian:buster-slim

RUN apt update
RUN apt upgrade -y
RUN apt install apache2 -y
RUN a2enmod include
RUN service apache2 restart

COPY 000-default.conf /etc/apache2/sites-available

ENV APACHE_RUN_USER www-data
ENV APACHE_RUN_GROUP www-data
ENV APACHE_RUN_DIR /var/run/apache2
ENV APACHE_LOG_DIR /var/log/apache2
ENV APACHE_PID_FILE $APACHE_RUN_DIR/apache2.pid

EXPOSE 80

CMD ["/usr/sbin/apache2", "-D", "FOREGROUND"]
