CREATE DATABASE book_manager;
CREATE USER spring IDENTIFIED BY 'boot';
GRANT ALL PRIVILEGES ON book_manager.* TO spring;

#book_manager.xxx のユーザに権限を与えている