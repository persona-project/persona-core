mkdir -p /opt/persona/
tar -xf flume.tar -C /opt/persona/
cp persona-core-offline.jar persona-core-realtime.jar persona-backend.jar /opt/persona
cp redis.conf /etc/redis.conf
cp *.service /etc/systemd/system
cp *.timer /etc/systemd/system

