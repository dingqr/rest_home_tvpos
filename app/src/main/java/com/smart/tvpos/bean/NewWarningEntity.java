package com.smart.tvpos.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoJo on 2018/6/26.
 * wechat:18510829974
 * description: 监控概览页面-最新报警列表实体
 */

public class NewWarningEntity {
    /**
     * id : 2410
     * title : 用户 (童芯) 发出了求助信息
     * inOutId : 257
     * userId : 257
     * typeChild : 一键求助
     * branchId : 1
     * contact1 : null
     * telephone1 : null
     * relation1 : null
     * contact2 : null
     * telephone2 : null
     * relation2 : null
     * contactNo : HF201806051528132073
     * contactType : 有效
     * contactBegin : 2018-06-08
     * contactEnd : 2018-06-16
     * contactFile :
     * content : null
     * liveType : 长托
     * abilityId : 4
     * nurseId : 1
     * bedId : 20
     * outStatus : 入住
     * outDate : null
     * outType : null
     * outReason : null
     * outAdminId : null
     * outAudit :
     * outAuditReason : null
     * outAuditAdminId : null
     * moneyBase : 22200
     * moneyConsume : 0
     * moneyMedical : 0
     * moneyDeposit : 0
     * feeBed : 0
     * feeNurse : 0
     * feeEat : 0
     * feeManage : 0
     * admitId : 0
     * isPayment : 否
     * created : 2018-04-04 20:51:07
     * updated : 2018-06-05 01:10:00
     * adminUserId : 29
     * userName : 童芯
     * bedName : 10号床位
     * roomId : 2
     * floorId : 1
     * buildingId : 1
     * roomName : 103
     * floorName : 1F
     * buildingName : 哈福主楼
     * staff : [{"staffId":69,"name":"蔡总","id":69}]
     */

    private int id;
    private String title;
    private int inOutId;
    private int userId;
    private String typeChild;
    private int branchId;
    private Object contact1;
    private Object telephone1;
    private Object relation1;
    private Object contact2;
    private Object telephone2;
    private Object relation2;
    private String contactNo;
    private String contactType;
    private String contactBegin;
    private String contactEnd;
    private String contactFile;
    private Object content;
    private String liveType;
    private int abilityId;
    private int nurseId;
    private int bedId;
    private String outStatus;
    private Object outDate;
    private Object outType;
    private Object outReason;
    private Object outAdminId;
    private String outAudit;
    private Object outAuditReason;
    private Object outAuditAdminId;
    private int moneyBase;
    private int moneyConsume;
    private int moneyMedical;
    private int moneyDeposit;
    private int feeBed;
    private int feeNurse;
    private int feeEat;
    private int feeManage;
    private int admitId;
    private String isPayment;
    private String created;
    private String updated;
    private int adminUserId;
    private String userName;
    private String bedName;
    private int roomId;
    private int floorId;
    private int buildingId;
    private String roomName;
    private String floorName;
    private String buildingName;
    private List<StaffBean> staff;
    private String allStaff;//展示的所有护工，、隔开
    private String headImg;//老人头像

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getInOutId() {
        return inOutId;
    }

    public void setInOutId(int inOutId) {
        this.inOutId = inOutId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTypeChild() {
        return typeChild == null ? "" : typeChild;
    }

    public void setTypeChild(String typeChild) {
        this.typeChild = typeChild;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public Object getContact1() {
        return contact1;
    }

    public void setContact1(Object contact1) {
        this.contact1 = contact1;
    }

    public Object getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(Object telephone1) {
        this.telephone1 = telephone1;
    }

    public Object getRelation1() {
        return relation1;
    }

    public void setRelation1(Object relation1) {
        this.relation1 = relation1;
    }

    public Object getContact2() {
        return contact2;
    }

    public void setContact2(Object contact2) {
        this.contact2 = contact2;
    }

    public Object getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(Object telephone2) {
        this.telephone2 = telephone2;
    }

    public Object getRelation2() {
        return relation2;
    }

    public void setRelation2(Object relation2) {
        this.relation2 = relation2;
    }

    public String getContactNo() {
        return contactNo == null ? "" : contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getContactType() {
        return contactType == null ? "" : contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getContactBegin() {
        return contactBegin == null ? "" : contactBegin;
    }

    public void setContactBegin(String contactBegin) {
        this.contactBegin = contactBegin;
    }

    public String getContactEnd() {
        return contactEnd == null ? "" : contactEnd;
    }

    public void setContactEnd(String contactEnd) {
        this.contactEnd = contactEnd;
    }

    public String getContactFile() {
        return contactFile == null ? "" : contactFile;
    }

    public void setContactFile(String contactFile) {
        this.contactFile = contactFile;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getLiveType() {
        return liveType == null ? "" : liveType;
    }

    public void setLiveType(String liveType) {
        this.liveType = liveType;
    }

    public int getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(int abilityId) {
        this.abilityId = abilityId;
    }

    public int getNurseId() {
        return nurseId;
    }

    public void setNurseId(int nurseId) {
        this.nurseId = nurseId;
    }

    public int getBedId() {
        return bedId;
    }

    public void setBedId(int bedId) {
        this.bedId = bedId;
    }

    public String getOutStatus() {
        return outStatus == null ? "" : outStatus;
    }

    public void setOutStatus(String outStatus) {
        this.outStatus = outStatus;
    }

    public Object getOutDate() {
        return outDate;
    }

    public void setOutDate(Object outDate) {
        this.outDate = outDate;
    }

    public Object getOutType() {
        return outType;
    }

    public void setOutType(Object outType) {
        this.outType = outType;
    }

    public Object getOutReason() {
        return outReason;
    }

    public void setOutReason(Object outReason) {
        this.outReason = outReason;
    }

    public Object getOutAdminId() {
        return outAdminId;
    }

    public void setOutAdminId(Object outAdminId) {
        this.outAdminId = outAdminId;
    }

    public String getOutAudit() {
        return outAudit == null ? "" : outAudit;
    }

    public void setOutAudit(String outAudit) {
        this.outAudit = outAudit;
    }

    public Object getOutAuditReason() {
        return outAuditReason;
    }

    public void setOutAuditReason(Object outAuditReason) {
        this.outAuditReason = outAuditReason;
    }

    public Object getOutAuditAdminId() {
        return outAuditAdminId;
    }

    public void setOutAuditAdminId(Object outAuditAdminId) {
        this.outAuditAdminId = outAuditAdminId;
    }

    public int getMoneyBase() {
        return moneyBase;
    }

    public void setMoneyBase(int moneyBase) {
        this.moneyBase = moneyBase;
    }

    public int getMoneyConsume() {
        return moneyConsume;
    }

    public void setMoneyConsume(int moneyConsume) {
        this.moneyConsume = moneyConsume;
    }

    public int getMoneyMedical() {
        return moneyMedical;
    }

    public void setMoneyMedical(int moneyMedical) {
        this.moneyMedical = moneyMedical;
    }

    public int getMoneyDeposit() {
        return moneyDeposit;
    }

    public void setMoneyDeposit(int moneyDeposit) {
        this.moneyDeposit = moneyDeposit;
    }

    public int getFeeBed() {
        return feeBed;
    }

    public void setFeeBed(int feeBed) {
        this.feeBed = feeBed;
    }

    public int getFeeNurse() {
        return feeNurse;
    }

    public void setFeeNurse(int feeNurse) {
        this.feeNurse = feeNurse;
    }

    public int getFeeEat() {
        return feeEat;
    }

    public void setFeeEat(int feeEat) {
        this.feeEat = feeEat;
    }

    public int getFeeManage() {
        return feeManage;
    }

    public void setFeeManage(int feeManage) {
        this.feeManage = feeManage;
    }

    public int getAdmitId() {
        return admitId;
    }

    public void setAdmitId(int admitId) {
        this.admitId = admitId;
    }

    public String getIsPayment() {
        return isPayment == null ? "" : isPayment;
    }

    public void setIsPayment(String isPayment) {
        this.isPayment = isPayment;
    }

    public String getCreated() {
        return created == null ? "" : created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated == null ? "" : updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public int getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(int adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getUserName() {
        return userName == null ? "" : userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBedName() {
        return bedName == null ? "" : bedName;
    }

    public void setBedName(String bedName) {
        this.bedName = bedName;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getRoomName() {
        return roomName == null ? "" : roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getFloorName() {
        return floorName == null ? "" : floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getBuildingName() {
        return buildingName == null ? "" : buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public List<StaffBean> getStaff() {
        if (staff == null) {
            return new ArrayList<>();
        }
        return staff;
    }

    public void setStaff(List<StaffBean> staff) {
        this.staff = staff;
    }

    public String getAllStaff() {
        return allStaff == null ? "无" : allStaff;
    }

    public void setAllStaff(String allStaff) {
        this.allStaff = allStaff;
    }

    public String getHeadImg() {
        return headImg == null ? "" : headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    /**
     * 护理人员
     */
    public static class StaffBean {
        /**
         * staffId : 69
         * name : 蔡总
         * id : 69
         */

        private int staffId;
        private String name;
        private int id;

        public int getStaffId() {
            return staffId;
        }

        public void setStaffId(int staffId) {
            this.staffId = staffId;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}