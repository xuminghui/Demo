--这里的数据在启动和执行单元测试的时候，会被自动插入到库中。避免了手动生成数据代码的麻烦，这里包括了基础数据和测试数据
insert into book (id, author, book_name, isbn, remark) values (1, 'author', 'bookName', 'isbn', 'remark');