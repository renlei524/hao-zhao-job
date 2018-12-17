package leiren.haozhaojob.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import leiren.haozhaojob.common.validator.group.AddGroup;
import leiren.haozhaojob.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 地区信息
 * 
 * @author leiren
 * @email 349191849@qq.com
 * @date 2018-06-08 09:54:02
 */
@TableName("t_wb_area")
public class WbAreaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private  Integer id;

	/**
	 * 
	 */
	private Integer areaCode;
	/**
	 * 上一级的id值
	 */
    @NotNull(message = "上级地区名称不能为空",groups = {AddGroup.class,UpdateGroup.class})
	private Integer parentCode;
	/**
	 * 上一级菜单名字
	 */
	@TableField(exist=false)
	private String parentName;
	/**
	 * 地区名称
	 */
	@NotBlank(message = "地区名称不能为空",groups = {AddGroup.class,UpdateGroup.class})
	private String name;
	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * ztree属性
	 */
	@TableField(exist=false)
	private Boolean open;
	@TableField(exist=false)
	private List<?> list;



	/**
	 * 设置：
	 */
	public void setAreaCode(Integer areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * 获取：
	 */
	public Integer getAreaCode() {
		return areaCode;
	}
	/**
	 * 设置：上一级的id值
	 */
	public void setParentCode(Integer parentCode) {
		this.parentCode = parentCode;
	}
	/**
	 * 获取：上一级的id值
	 */
	public Integer getParentCode() {
		return parentCode;
	}
	/**
	 * 设置：地区名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：地区名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：排序
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：排序
	 */
	public Integer getSort() {
		return sort;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
