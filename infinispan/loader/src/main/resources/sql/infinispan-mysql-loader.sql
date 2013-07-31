CREATE DATABASE infinispan_loader;
create user 'infinispan_user'@'localhost' identified by 'infinispan_pass';
grant all on infinispan_loader.* to infinispan_user@'localhost';
FLUSH PRIVILEGES;



