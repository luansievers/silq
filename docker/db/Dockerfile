FROM postgres:9.5-alpine
ADD SQL/1.sql /docker-entrypoint-initdb.d/
ADD SQL/2.sql /docker-entrypoint-initdb.d/
ADD SQL/3.sql /docker-entrypoint-initdb.d/
ENV POSTGRES_USER postgres
ENV POSTGRES_DB silq2

