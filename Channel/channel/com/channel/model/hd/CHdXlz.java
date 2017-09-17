package com.channel.model.hd;

import java.util.Date;

import com.channel.bean.PropDesc;
import com.channel.modelutil.anotation.UiInput;
import com.channel.modelutil.anotation.UiInputValidator;
import com.channel.modelutil.anotation.Validator;

/**
 * CHdXlzjcxx entity. @author MyEclipse Persistence Tools
 */

public class CHdXlz implements java.io.Serializable {

    // Fields
    @PropDesc("系缆桩")
    private String xlzname;

    private Integer id;

    @UiInputValidator({@Validator(filter = "notempty"),
            @Validator(filter = "length", min = 6, max = 11, msg = "输入必须为11个数字"),
            @Validator(filter = "number"),
            @Validator(filter = "user",fn = "appuniquecheck",msg = "编号已存在")
    })
    @UiInput(groupname = "基础信息", group = 1, order = 1, desc = "编号", must = true)
    @PropDesc("编号")
    private String bh;

    @UiInputValidator({@Validator(filter = "notempty"), @Validator(filter = "length", min = 0, max = 32)})
    @UiInput(group = 1, order = 2, desc = "名称", must = true)
    @PropDesc("名称")
    private String mc;

    @UiInput(group = 1, order = 3, desc = "材质", must = false)
    @PropDesc("材质")
    private String cz;

    @UiInput(group = 1, order = 4, desc = "形状", must = false)
    @PropDesc("形状")
    private String xz;

    @UiInput(group = 1, order = 5, desc = "是否双柱", must = false, inputtype = "selectyesno")
    @PropDesc("是否双柱")
    private Integer sfsz;

    @UiInput(group = 1, order = 6, desc = "生产厂家", must = false)
    @PropDesc("生产厂家")
    private String sccj;

    @PropDesc("附属物类别")
    private Integer fswlx;

    @UiInput(groupname = "地理信息", group = 2, order = 1, desc = "所属航道编号", must = true, defaultvalfromweb = "hdaoid", readonly = true, hidden = true)
    @PropDesc("所属航道编号")
    private Integer sshdaoid;

    @UiInput(group = 2, order = 2, desc = "所属航段编号", must = true, defaultvalfromweb = "hduanid", readonly = true, hidden = true)
    @PropDesc("所属航段编号")
    private Integer sshduanid;

    @UiInputValidator({@Validator(filter = "notempty", attr = "selitem")})
    @UiInput(group = 2, order = 3, desc = "所在行政区划", must = true, inputtype = "selectxzqh", defaultvalfromweb = "szxzqh", readonly = true)
    @PropDesc("所在行政区划")
    private Integer szxzqh;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 4, desc = "中心点经度", must = false)
    @PropDesc("中心点经度")
    private Double jd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 5, desc = "中心点纬度", must = false)
    @PropDesc("中心点纬度")
    private Double wd;

    @UiInput(group = 2, order = 6, desc = "所在详细地址", must = false)
    @PropDesc("所在详细地址")
    private String szxxdz;

    @UiInputValidator(@Validator(filter = "length", min = 0, max = 32))
    @UiInput(groupname = "辅助信息", group = 3, order = 1, desc = "管养单位名称", must = false)
    @PropDesc("管养单位名称")
    private String gljgmc;

    @UiInput(group = 3, order = 3, desc = "管养单位联系人", must = false)
    @PropDesc("管养单位联系人")
    private String gljglxr;

    @UiInputValidator({@Validator(filter = "phone")})
    @UiInput(group = 3, order = 4, desc = "管养单位联系电话", must = false)
    @PropDesc("管养单位联系电话")
    private String gljglxdh;

    @UiInput(group = 3, order = 2, desc = "管养单位所在行政区划", must = false, inputtype = "selectxzqh")
    @PropDesc("管养单位所在行政区划")
    private Integer gljgszxzqh;

    @UiInput(group = 3, order = 5, desc = "备注", must = false, inputtype = "textarea", oneline = true)
    @PropDesc("备注")
    private String bz;
    @PropDesc("创建人")
    private Integer creater;
    @PropDesc("创建时间")
    private Date createtime;
    @PropDesc("更新时间")
    private Date updatetime;

    // Constructors

    /**
     * default constructor
     */
    public CHdXlz() {
    }

    public CHdXlz(Integer id, String bh, String mc, String cz, String xz,
                  Integer sfsz, String sccj, Integer fswlx, Integer sshdaoid,
                  Integer sshduanid, Integer szxzqh, Double jd, Double wd,
                  String szxxdz, String gljgmc, String gljglxr, String gljglxdh,
                  Integer gljgszxzqh, String bz, Integer creater, Date createtime,
                  Date updatetime) {
        super();
        this.id = id;
        this.bh = bh;
        this.mc = mc;
        this.cz = cz;
        this.xz = xz;
        this.sfsz = sfsz;
        this.sccj = sccj;
        this.fswlx = fswlx;
        this.sshdaoid = sshdaoid;
        this.sshduanid = sshduanid;
        this.szxzqh = szxzqh;
        this.jd = jd;
        this.wd = wd;
        this.szxxdz = szxxdz;
        this.gljgmc = gljgmc;
        this.gljglxr = gljglxr;
        this.gljglxdh = gljglxdh;
        this.gljgszxzqh = gljgszxzqh;
        this.bz = bz;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CHdXlz(String bh, String mc, String cz, String xz, Integer sfsz,
                  String sccj, Integer fswlx, Integer sshdaoid, Integer sshduanid,
                  Integer szxzqh, Double jd, Double wd, String szxxdz,
                  String gljgmc, String gljglxr, String gljglxdh, Integer gljgszxzqh,
                  String bz, Integer creater, Date createtime, Date updatetime) {
        super();
        this.bh = bh;
        this.mc = mc;
        this.cz = cz;
        this.xz = xz;
        this.sfsz = sfsz;
        this.sccj = sccj;
        this.fswlx = fswlx;
        this.sshdaoid = sshdaoid;
        this.sshduanid = sshduanid;
        this.szxzqh = szxzqh;
        this.jd = jd;
        this.wd = wd;
        this.szxxdz = szxxdz;
        this.gljgmc = gljgmc;
        this.gljglxr = gljglxr;
        this.gljglxdh = gljglxdh;
        this.gljgszxzqh = gljgszxzqh;
        this.bz = bz;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    // Property accessors

    public Integer getSzxzqh() {
        return szxzqh;
    }

    public void setSzxzqh(Integer szxzqh) {
        this.szxzqh = szxzqh;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBh() {
        return this.bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getMc() {
        return this.mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public Integer getFswlx() {
        return this.fswlx;
    }

    public void setFswlx(Integer fswlx) {
        this.fswlx = fswlx;
    }

    public Integer getSshdaoid() {
        return sshdaoid;
    }

    public void setSshdaoid(Integer sshdaoid) {
        this.sshdaoid = sshdaoid;
    }

    public Integer getSshduanid() {
        return sshduanid;
    }

    public void setSshduanid(Integer sshduanid) {
        this.sshduanid = sshduanid;
    }

    public String getCz() {
        return this.cz;
    }

    public void setCz(String cz) {
        this.cz = cz;
    }

    public String getXz() {
        return this.xz;
    }

    public void setXz(String xz) {
        this.xz = xz;
    }

    public Integer getSfsz() {
        return this.sfsz;
    }

    public void setSfsz(Integer sfsz) {
        this.sfsz = sfsz;
    }

    public Double getJd() {
        return jd;
    }

    public void setJd(Double jd) {
        this.jd = jd;
    }

    public Double getWd() {
        return wd;
    }

    public void setWd(Double wd) {
        this.wd = wd;
    }

    public String getSccj() {
        return sccj;
    }

    public void setSccj(String sccj) {
        this.sccj = sccj;
    }

    public String getSzxxdz() {
        return szxxdz;
    }

    public void setSzxxdz(String szxxdz) {
        this.szxxdz = szxxdz;
    }

    public String getGljgmc() {
        return this.gljgmc;
    }

    public void setGljgmc(String gljgmc) {
        this.gljgmc = gljgmc;
    }

    public String getGljglxr() {
        return this.gljglxr;
    }

    public void setGljglxr(String gljglxr) {
        this.gljglxr = gljglxr;
    }

    public String getGljglxdh() {
        return this.gljglxdh;
    }

    public void setGljglxdh(String gljglxdh) {
        this.gljglxdh = gljglxdh;
    }

    public Integer getGljgszxzqh() {
        return this.gljgszxzqh;
    }

    public void setGljgszxzqh(Integer gljgszxzqh) {
        this.gljgszxzqh = gljgszxzqh;
    }

    public String getBz() {
        return this.bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Integer getCreater() {
        return this.creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}