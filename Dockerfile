FROM openjdk:8
ENV PATH="/chess-server/build/install/chess-server/bin:/root/bin:${PATH}"
COPY 99fixbadproxy /etc/apt/apt.conf.d/ 
RUN apt-get clean \
    && apt-get update \
    && apt-get install -y --no-install-recommends maven vim qemu-system-x86 qemu-utils \
    && apt-get upgrade -y \
    && rm -rf /var/lib/apt/lists/* 
RUN wget -O /tmp/capstan-install.sh https://raw.githubusercontent.com/mikelangelo-project/capstan/master/scripts/download \
    && HOSTTYPE=x86_64 OSTYPE='linux-gnu' sh /tmp/capstan-install.sh \
    && rm -rf /tmp/capstan-install.sh \
    && /root/bin/capstan pull mike/osv-loader \
    && /root/bin/capstan package pull openjdk8-zulu-full \
    && /root/bin/capstan package pull openjdk8-zulu-compact1 \
    && /root/bin/capstan package pull osv.bootstrap
COPY . chess-server
RUN (cd chess-server; ./gradlew build installDist -x test) 
EXPOSE 8080
CMD /chess-server/docker-utils/show-ip.sh && chess-server

