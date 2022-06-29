CREATE TABLE POSTS(                               
  id INT(11) NOT NULL AUTO_INCREMENT,                
  userId VARCHAR(20) NOT NULL,                          
  title VARCHAR(20) NOT NULL,                      
  ctt TEXT,                                   
  filename VARCHAR(20) ,                       
  useYn VARCHAR(20) DEFAULT 'Y',                      
  url VARCHAR(20) ,                      
  size INT(20) ,                      
  regDate  DATETIME,                                     
  CONSTRAINT POSTS_PK PRIMARY KEY(id)          
);