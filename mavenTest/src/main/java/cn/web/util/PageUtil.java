package cn.web.util;

import java.util.ArrayList;
import java.util.List;




//��ҳ
public class PageUtil<T> {
       public Integer index= 1;//��ǰҳ
       public Integer size = 3;//ÿҳ��ʾ������
       public Integer totalPage;//��ҳ��
       public Integer totalCount;//�ܼ�¼��
       public List<T> list = new ArrayList<T>();//�����¼
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
       
}
