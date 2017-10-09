FROM centos:centos7

#Install SSH
RUN \
  yum -y install openssh-clients openssh-server && \
  yum -y clean all && \
  touch /run/utmp && \
  chmod u+s /usr/bin/ping

#Install Git
RUN yum install -y git
RUN mkdir /root/st_versalex/
WORKDIR /root/st_versalex/
#ARG st_username
#ARG st_password
#RUN git clone -b S-11540-merge-versalex-ansible-code https://$st_username:$st_password@github.com/CleoDev/st.git

# Install EPEL repo and Python deps
RUN yum clean all && \
    yum -y install epel-release && \
    yum -y install PyYAML python-jinja2 python-httplib2 python-keyczar python-paramiko python-setuptools git python-pip
RUN mkdir /etc/ansible/
ARG ansible_ver=2.3.1.0

# Install ansible specific version
RUN yum -y install ansible-$ansible_ver
RUN echo -e '[local]\nlocalhost' > /etc/ansible/hosts
RUN sed -i 's/#hash_behaviour = replace/hash_behaviour = merge/g' /etc/ansible/ansible.cfg

#Install required dependencies
RUN yum update -y && yum install -y \
    wget \
    tar.x86_64
    
#install oracle jdk8
RUN cd /opt/
WORKDIR /opt/
RUN wget -P /opt/ --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u144-b01/090f390dda5b47b9b721c7dfaa008135/jdk-8u144-linux-x64.tar.gz"
RUN tar xzf /opt/jdk-8u144-linux-x64.tar.gz -C /opt
RUN rm /opt/jdk-8u144-linux-x64.tar.gz

RUN cd /opt/jdk1.8.0_144/

RUN alternatives --install /usr/bin/java java /opt/jdk1.8.0_144/bin/java 2 \
&& alternatives --install /usr/bin/javac javac /opt/jdk1.8.0_144/bin/javac 2 \
&& alternatives --set javac /opt/jdk1.8.0_144/bin/javac

ENV JAVA_HOME /opt/jdk1.8.0_144
ENV JRE_HOME /opt/jdk1.8.0_144/jre
ENV PATH $PATH:/opt/jdk1.8.0_144/bin:/opt/jdk1.8.0_144/jre/bin

#Installs Maven 3.3.9
ARG MVN_VERSION=3.3.9
ARG SHA=beb91419245395bd69a4a6edad5ca3ec1a8b64e41457672dc687c173a495f034
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MVN_VERSION}/binaries

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MVN_VERSION}-bin.tar.gz \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
  
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"  

EXPOSE 22

CMD ["mvn"]