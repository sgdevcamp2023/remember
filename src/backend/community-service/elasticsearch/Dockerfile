FROM docker.elastic.co/elasticsearch/elasticsearch:7.10.2
RUN elasticsearch-plugin install --batch analysis-nori
RUN elasticsearch-plugin install -b https://github.com/opendistro-for-elasticsearch/alerting/releases/download/v1.13.1.0/alerting.zip