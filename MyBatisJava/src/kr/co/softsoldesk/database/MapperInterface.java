package kr.co.softsoldesk.database;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.co.softsoldesk.beans.DataBean1;

public interface MapperInterface {
	
	@Insert("insert into spring_mvc_table(data1, data2, data3) values(#{data1}, #{data2}, #{data3}) ")
	void insert_data(DataBean1 dataBean1);
	
	@Select("select data1, data2, data3 from spring_mvc_table")
	List<DataBean1> select_data();

	
}
