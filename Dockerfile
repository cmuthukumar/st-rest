FROM centos:centos7

#Install SSH
RUN \
  yum -y install openssh-clients openssh-server && \
  yum -y clean all

# Install EPEL repo and Python deps
RUN yum -y install epel-release
RUN yum -y install PyYAML python-jinja2 python-httplib2 python-keyczar python-paramiko python-setuptools  python-pip python-lxml
RUN mkdir /etc/ansible/
ARG ansible_ver=2.3.1.0

# Install ansible specific version
RUN yum -y install ansible-$ansible_ver
RUN echo -e '[local]\nlocalhost' > /etc/ansible/hosts
RUN sed -i 's/#hash_behaviour = replace/hash_behaviour = merge/g' /etc/ansible/ansible.cfg
RUN sed -i 's/#host_key_checking = False/host_key_checking = False/g' /etc/ansible/ansible.cfg

#Install required dependencies
RUN yum install -y \
    wget \
    tar.x86_64
    
#install oracle jdk8
RUN cd /opt/
WORKDIR /opt/
RUN wget -P /opt/ --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u144-b01/090f390dda5b47b9b721c7dfaa008135/jdk-8u144-linux-x64.tar.gz"
#RUN wget -P /opt/ --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u144-b01/090f390dda5b47b9b721c7dfaa008135/jdk-8u144-linux-x64.rpm"
RUN tar xzf /opt/jdk-8u144-linux-x64.tar.gz -C /opt
RUN rm /opt/jdk-8u144-linux-x64.tar.gz
RUN cd /opt/jdk1.8.0_144/

RUN alternatives --install /usr/bin/java java /opt/jdk1.8.0_144/bin/java 2 \
&& alternatives --install /usr/bin/javac javac /opt/jdk1.8.0_144/bin/javac 2 \
&& alternatives --set javac /opt/jdk1.8.0_144/bin/javac

ENV JAVA_HOME /opt/jdk1.8.0_144
ENV JRE_HOME /opt/jdk1.8.0_144/jre
ENV PATH $PATH:/opt/jdk1.8.0_144/bin:/opt/jdk1.8.0_144/jre/bin

EXPOSE 22

CMD ["java"]