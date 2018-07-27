package com.smart.tvpos.bean;

import java.util.List;

/**
 * Created by JoJo on 2018/6/26.
 * wechat:18510829974
 * description: 用户护理进度接口返回实体
 */

public class UserNurseDataEntity {


    /**
     * branch : 上海哈福养老院合同   分院name
     * user : [{"bedId":105,"userId":1,"inOutId":1,"userName":"萧平章","birth":"1955-11-22","headImg":"/upload/api/credential/1_user20180622042400.jpg","sex":"男","id":1,"bedName":"1号床位","roomId":21,"floorId":6,"buildingId":1,"roomName":"601","floorName":"6F","buildingName":"哈福主楼","age":62,"numA":14,"numF":14,"endTimeMax":null},{"bedId":106,"userId":2,"inOutId":2,"userName":"林溪","birth":"1939-03-15","headImg":"/upload/api/credential/2_user20180509105525.jpg","sex":"女","id":2,"bedName":"1号床位","roomId":22,"floorId":6,"buildingId":1,"roomName":"602","floorName":"6F","buildingName":"哈福主楼","age":79,"numA":8,"numF":8,"endTimeMax":null},{"bedId":108,"userId":3,"inOutId":3,"userName":"萧元启","birth":"1939-03-15","headImg":"/static/admin/images/oldman.png","sex":"男","id":3,"bedName":"1号床位","roomId":23,"floorId":6,"buildingId":1,"roomName":"603","floorName":"6F","buildingName":"哈福主楼","age":79,"numA":8,"numF":8,"endTimeMax":null},{"bedId":109,"userId":4,"inOutId":4,"userName":"何成","birth":"1960-11-22","headImg":"/upload/api/credential/4_user20180509050544.jpg","sex":"男","id":4,"bedName":"1号床位","roomId":24,"floorId":6,"buildingId":1,"roomName":"604","floorName":"6F","buildingName":"哈福主楼","age":57,"numA":6,"numF":6,"endTimeMax":null},{"bedId":110,"userId":5,"inOutId":5,"userName":"张慧文","birth":"1950-11-22","headImg":"/upload/api/credential/5_user20180509050634.jpg","sex":"女","id":5,"bedName":"1号床位","roomId":25,"floorId":6,"buildingId":1,"roomName":"605","floorName":"6F","buildingName":"哈福主楼","age":67},{"bedId":111,"userId":6,"inOutId":6,"userName":"刘浩然","birth":"1939-03-15","headImg":"/upload/images/201801112354304477.png","sex":"男","id":6,"bedName":"1号床位","roomId":26,"floorId":6,"buildingId":1,"roomName":"606","floorName":"6F","buildingName":"哈福主楼","age":79},{"bedId":1,"userId":7,"inOutId":7,"userName":"王慧文3","birth":"1939-03-15","headImg":"/upload/api/credential/7_user20180509115912.jpg","sex":"女","id":7,"bedName":"1号床位","roomId":1,"floorId":1,"buildingId":1,"roomName":"101","floorName":"1F","buildingName":"哈福主楼","age":79,"numA":6,"numF":6,"endTimeMax":null},{"bedId":2,"userId":8,"inOutId":8,"userName":"萧平旗4","birth":"1939-03-15","headImg":"/upload/api/credential/8_user20180626102705.jpg","sex":"男","id":8,"bedName":"2号床位","roomId":1,"floorId":1,"buildingId":1,"roomName":"101","floorName":"1F","buildingName":"哈福主楼","age":79,"numA":8,"numF":8,"endTimeMax":null},{"bedId":3,"userId":9,"inOutId":9,"userName":"林杨","birth":"1941-06-15","headImg":"/upload/images/201801112357115786.png","sex":"男","id":9,"bedName":"3号床位","roomId":1,"floorId":1,"buildingId":1,"roomName":"101","floorName":"1F","buildingName":"哈福主楼","age":77,"numA":6,"numF":6,"endTimeMax":null,"typeChild":"心率异常"},{"bedId":4,"userId":10,"inOutId":10,"userName":"刘勤勤","birth":"1939-03-15","headImg":"/upload/images/201801112358178411.png","sex":"女","id":10,"bedName":"4号床位","roomId":1,"floorId":1,"buildingId":1,"roomName":"101","floorName":"1F","buildingName":"哈福主楼","age":79,"numA":6,"numF":6,"endTimeMax":null},{"bedId":11,"userId":11,"inOutId":11,"userName":"李维斯","birth":"1939-09-15","headImg":"/upload/api/credential/11_user20180509115929.jpg","sex":"男","id":11,"bedName":"1号床位","roomId":2,"floorId":1,"buildingId":1,"roomName":"103","floorName":"1F","buildingName":"哈福主楼","age":78,"numA":6,"numF":6,"endTimeMax":null},{"bedId":5,"userId":12,"inOutId":12,"userName":"王剑锋","birth":"1952-11-22","headImg":"/upload/images/201801120000119217.png","sex":"男","id":12,"bedName":"5号床位","roomId":1,"floorId":1,"buildingId":1,"roomName":"101","floorName":"1F","buildingName":"哈福主楼","age":65,"numA":6,"numF":6,"endTimeMax":null},{"bedId":6,"userId":13,"inOutId":13,"userName":"王丽丽","birth":"1962-11-22","headImg":"/upload/images/201801120000472243.png","sex":"女","id":13,"bedName":"6号床位","roomId":1,"floorId":1,"buildingId":1,"roomName":"101","floorName":"1F","buildingName":"哈福主楼","age":55},{"bedId":7,"userId":14,"inOutId":14,"userName":"刘宏林","birth":"1945-03-15","headImg":"/upload/images/201806190956029747.png","sex":"男","id":14,"bedName":"7号床位","roomId":1,"floorId":1,"buildingId":1,"roomName":"101","floorName":"1F","buildingName":"哈福主楼","age":73,"numA":7,"numF":7,"endTimeMax":null},{"bedId":8,"userId":15,"inOutId":15,"userName":"陈思路","birth":"1939-03-15","headImg":"/upload/images/201801120002282637.png","sex":"男","id":15,"bedName":"8号床位","roomId":1,"floorId":1,"buildingId":1,"roomName":"101","floorName":"1F","buildingName":"哈福主楼","age":79,"numA":7,"numF":7,"endTimeMax":null},{"bedId":9,"userId":16,"inOutId":16,"userName":"李秀丽","birth":"1939-03-15","headImg":"/upload/images/201806190956525711.png","sex":"女","id":16,"bedName":"9号床位","roomId":1,"floorId":1,"buildingId":1,"roomName":"101","floorName":"1F","buildingName":"哈福主楼","age":79,"numA":7,"numF":7,"endTimeMax":null},{"bedId":10,"userId":17,"inOutId":17,"userName":"朱元旗","birth":"1938-03-15","headImg":"/upload/images/201801120003579780.png","sex":"男","id":17,"bedName":"10号床位","roomId":1,"floorId":1,"buildingId":1,"roomName":"101","floorName":"1F","buildingName":"哈福主楼","age":80,"numA":6,"numF":6,"endTimeMax":null},{"bedId":12,"userId":18,"inOutId":18,"userName":"刘秀丽","birth":"1939-03-15","headImg":"/upload/api/credential/18_user20180626102718.jpg","sex":"女","id":18,"bedName":"2号床位","roomId":2,"floorId":1,"buildingId":1,"roomName":"103","floorName":"1F","buildingName":"哈福主楼","age":79,"numA":6,"numF":6,"endTimeMax":null},{"bedId":13,"userId":19,"inOutId":19,"userName":"王星龙","birth":"1936-03-15","headImg":"/upload/images/201801120005047485.png","sex":"男","id":19,"bedName":"3号床位","roomId":2,"floorId":1,"buildingId":1,"roomName":"103","floorName":"1F","buildingName":"哈福主楼","age":82,"numA":6,"numF":6,"endTimeMax":null},{"bedId":14,"userId":20,"inOutId":20,"userName":"徐林婷","birth":"1938-06-15","headImg":"/upload/images/201801120005355451.png","sex":"女","id":20,"bedName":"4号床位","roomId":2,"floorId":1,"buildingId":1,"roomName":"103","floorName":"1F","buildingName":"哈福主楼","age":80,"numA":6,"numF":6,"endTimeMax":null},{"bedId":15,"userId":21,"inOutId":21,"userName":"李蓝峰","birth":"1942-03-15","headImg":"/upload/images/201801120006143616.png","sex":"男","id":21,"bedName":"5号床位","roomId":2,"floorId":1,"buildingId":1,"roomName":"103","floorName":"1F","buildingName":"哈福主楼","age":76,"numA":6,"numF":6,"endTimeMax":null},{"bedId":16,"userId":22,"inOutId":22,"userName":"张晓强","birth":"1935-03-15","headImg":"/upload/images/201801120006486243.png","sex":"男","id":22,"bedName":"6号床位","roomId":2,"floorId":1,"buildingId":1,"roomName":"103","floorName":"1F","buildingName":"哈福主楼","age":83},{"bedId":17,"userId":23,"inOutId":23,"userName":"刘璐璐","birth":"1946-03-15","headImg":"/upload/images/201801120007238750.png","sex":"女","id":23,"bedName":"7号床位","roomId":2,"floorId":1,"buildingId":1,"roomName":"103","floorName":"1F","buildingName":"哈福主楼","age":72},{"bedId":18,"userId":24,"inOutId":24,"userName":"赵宏图","birth":"1935-03-15","headImg":"/upload/images/201801120007563331.png","sex":"男","id":24,"bedName":"8号床位","roomId":2,"floorId":1,"buildingId":1,"roomName":"103","floorName":"1F","buildingName":"哈福主楼","age":83},{"bedId":0,"userId":25,"inOutId":25,"userName":"测试01","birth":null,"headImg":"/static/admin/images/oldman.png","sex":"男","id":25,"bedName":"","roomId":"","floorId":"","buildingId":"","roomName":"","floorName":"","buildingName":"","age":0},{"bedId":0,"userId":26,"inOutId":26,"userName":"李雪","birth":"1954-01-15","headImg":"/static/admin/images/oldlady.png","sex":"女","id":26,"bedName":"","roomId":"","floorId":"","buildingId":"","roomName":"","floorName":"","buildingName":"","age":64},{"bedId":47,"userId":27,"inOutId":27,"userName":"李雪","birth":"1954-01-15","headImg":"/static/admin/images/oldlady.png","sex":"女","id":27,"bedName":"2号床位","roomId":6,"floorId":2,"buildingId":1,"roomName":"202女","floorName":"2F","buildingName":"哈福主楼","age":64},{"bedId":68,"userId":28,"inOutId":28,"userName":"王大牛","birth":"1942-01-04","headImg":"/static/admin/images/oldman.png","sex":"男","id":28,"bedName":"3号床位","roomId":10,"floorId":3,"buildingId":1,"roomName":"302男","floorName":"3F","buildingName":"哈福主楼","age":76},{"bedId":48,"userId":29,"inOutId":29,"userName":"米兰","birth":"1936-02-05","headImg":"/upload/api/credential/29_user20180525112444.jpg","sex":"女","id":29,"bedName":"3号床位","roomId":6,"floorId":2,"buildingId":1,"roomName":"202女","floorName":"2F","buildingName":"哈福主楼","age":82,"numA":1,"numF":1,"endTimeMax":null,"typeChild":"进入报警"},{"bedId":41,"userId":30,"inOutId":30,"userName":"李萌萌","birth":"1945-01-16","headImg":"/static/admin/images/oldlady.png","sex":"女","id":30,"bedName":"1号床位","roomId":5,"floorId":2,"buildingId":1,"roomName":"201女","floorName":"2F","buildingName":"哈福主楼","age":73,"numA":1,"numF":1,"endTimeMax":null},{"bedId":null,"userId":31,"inOutId":31,"userName":"19号上线","birth":"1962-11-22","headImg":"/static/admin/images/oldman.png","sex":"男","id":31,"bedName":"","roomId":"","floorId":"","buildingId":"","roomName":"","floorName":"","buildingName":"","age":55},{"bedId":67,"userId":32,"inOutId":32,"userName":"叶山","birth":"1945-08-16","headImg":"/static/admin/images/oldman.png","sex":"男","id":32,"bedName":"2号床位","roomId":10,"floorId":3,"buildingId":1,"roomName":"302男","floorName":"3F","buildingName":"哈福主楼","age":72},{"bedId":19,"userId":106,"inOutId":106,"userName":"测试0120","birth":null,"headImg":"/static/admin/images/oldman.png","sex":"男","id":106,"bedName":"9号床位","roomId":2,"floorId":1,"buildingId":1,"roomName":"103","floorName":"1F","buildingName":"哈福主楼","age":0},{"bedId":null,"userId":243,"inOutId":243,"userName":"切","birth":"1964-03-05","headImg":"/static/admin/images/oldman.png","sex":"男","id":243,"bedName":"","roomId":"","floorId":"","buildingId":"","roomName":"","floorName":"","buildingName":"","age":54},{"bedId":20,"userId":257,"inOutId":257,"userName":"童芯","birth":"1943-04-04","headImg":"/upload/api/credential/257_user20180525112429.jpg","sex":"女","id":257,"bedName":"10号床位","roomId":2,"floorId":1,"buildingId":1,"roomName":"103","floorName":"1F","buildingName":"哈福主楼","age":75,"numA":7,"numF":7,"endTimeMax":null},{"bedId":61,"userId":264,"inOutId":264,"userName":"范老爷","birth":null,"headImg":"/static/admin/images/oldman.png","sex":"男","id":264,"bedName":"1号床位","roomId":9,"floorId":3,"buildingId":1,"roomName":"301男","floorName":"3F","buildingName":"哈福主楼","age":0,"numA":8,"numF":8,"endTimeMax":null,"typeChild":"进入报警"},{"bedId":62,"userId":265,"inOutId":265,"userName":"韦老爷","birth":null,"headImg":"/static/admin/images/oldman.png","sex":"男","id":265,"bedName":"2号床位","roomId":9,"floorId":3,"buildingId":1,"roomName":"301男","floorName":"3F","buildingName":"哈福主楼","age":0,"numA":4,"numF":4,"endTimeMax":null,"typeChild":"进入报警"},{"bedId":null,"userId":284,"inOutId":284,"userName":"张三","birth":null,"headImg":"/static/admin/images/oldman.png","sex":"男","id":284,"bedName":"","roomId":"","floorId":"","buildingId":"","roomName":"","floorName":"","buildingName":"","age":0,"numA":2,"numF":2,"endTimeMax":null},{"bedId":72,"userId":285,"inOutId":285,"userName":"哈福二","birth":"1954-06-16","headImg":"/static/admin/images/oldman.png","sex":"男","id":285,"bedName":"2号床位","roomId":11,"floorId":3,"buildingId":1,"roomName":"303男","floorName":"3F","buildingName":"哈福主楼","age":64,"numA":4,"numF":4,"endTimeMax":null},{"bedId":null,"userId":288,"inOutId":288,"userName":"哈福二","birth":"1954-06-16","headImg":"/static/admin/images/oldman.png","sex":"男","id":288,"bedName":"","roomId":"","floorId":"","buildingId":"","roomName":"","floorName":"","buildingName":"","age":64},{"bedId":0,"userId":289,"inOutId":289,"userName":"张三","birth":"1960-06-01","headImg":"/static/admin/images/oldman.png","sex":"男","id":289,"bedName":"","roomId":"","floorId":"","buildingId":"","roomName":"","floorName":"","buildingName":"","age":58},{"bedId":63,"userId":290,"inOutId":290,"userName":"张翔","birth":"1954-10-23","headImg":"/static/admin/images/oldman.png","sex":"男","id":290,"bedName":"3号床位","roomId":9,"floorId":3,"buildingId":1,"roomName":"301男","floorName":"3F","buildingName":"哈福主楼","age":63,"numA":2,"numF":2,"endTimeMax":null},{"bedId":66,"userId":291,"inOutId":291,"userName":"李斌","birth":null,"headImg":"/static/admin/images/oldman.png","sex":"男","id":291,"bedName":"1号床位","roomId":10,"floorId":3,"buildingId":1,"roomName":"302男","floorName":"3F","buildingName":"哈福主楼","age":0},{"bedId":null,"userId":292,"inOutId":292,"userName":"李斌","birth":null,"headImg":"/static/admin/images/oldman.png","sex":"男","id":292,"bedName":"","roomId":"","floorId":"","buildingId":"","roomName":"","floorName":"","buildingName":"","age":0},{"bedId":null,"userId":293,"inOutId":293,"userName":"李斌","birth":null,"headImg":"/static/admin/images/oldman.png","sex":"男","id":293,"bedName":"","roomId":"","floorId":"","buildingId":"","roomName":"","floorName":"","buildingName":"","age":0}]
     *    * branch	用户数据	 数组
     */

    private String branch;
    private List<UserNurseListEntity> user;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<UserNurseListEntity> getUser() {
        return user;
    }

    public void setUser(List<UserNurseListEntity> user) {
        this.user = user;
    }


}
