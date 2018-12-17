package leiren.haozhaojob.modules.operation.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 经营类目
 * 
 */
@TableName("t_vyicoo_category")
public class VyicooCategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 经营类目id
	 */
	private Integer id;
	/**
	 * 经营类目
	 */
	private String category;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
