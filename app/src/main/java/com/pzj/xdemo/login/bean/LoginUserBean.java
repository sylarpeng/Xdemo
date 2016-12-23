package com.pzj.xdemo.login.bean;



import java.util.List;

/**
 * Created by pzj on 2016/7/15.
 */
public class LoginUserBean extends BaseBean {


    /**
     * IdentityId : 1
     * Wx_open_id :
     * availableTotalRebate : 0.0
     * bankBinded : false
     * defaultStoreBgImg : http://vpclub-img.oss-cn-shenzhen.aliyuncs.com/upload/backgroundImage/putong02.png
     * differNextLevelSaleAmount : 1000.0
     * flage : 0
     * id : 13765149
     * isShowPhoneNo : 0
     * jpushGroupId : 100000058
     * jpushid : e2732510282246e9ab17d1ff8387a36a
     * levelName : 普通
     * levelNo : 1
     * monthSummary : []
     * myOrder : 0
     * nextLevelSaleAmount : 1000.0
     * nickName :
     * operatingTime : 1
     * otherContacts :
     * profit : 0.0
     * saleAmount : 0.0
     * salesTotalRebate : 0.0
     * storeLogo : http://vpclub-img.oss-cn-shenzhen.aliyuncs.com/upload/100000058logo.png
     * storeMasterGuid : 00000000-0000-0000-0000-000000000000
     * storeMasterId : 8784243
     * storeName : 18688451605
     * storeStrategyUrl : http://218.17.39.178:7007/html/storeStrategy.html
     * storeUrl : http://218.17.39.178:7007/lnyp/ztewd/13765149.html
     * storesNumber : 0
     * taskTotalRebate : 0.0
     * themeId : 0
     * token : 5C382127BCD32C505C60B93F2D73887AAAB02A8F24F966A891613FD0F94B018C0489BDCEC53A2F57
     * total : 0
     * totalgem : 0
     * userOrgConfig : {"address":"","company_contacts":"","man":"","numcode":"","orgid":0,"orgname":"","orgtype":0,"phone":"","salesvolume":""}
     * weixinNum :
     */

    private DataBean Data;
    /**
     * Data : {"IdentityId":1,"Wx_open_id":"","availableTotalRebate":0,"bankBinded":"false","defaultStoreBgImg":"http://vpclub-img.oss-cn-shenzhen.aliyuncs.com/upload/backgroundImage/putong02.png","differNextLevelSaleAmount":1000,"flage":0,"id":13765149,"isShowPhoneNo":0,"jpushGroupId":"100000058","jpushid":"e2732510282246e9ab17d1ff8387a36a","levelName":"普通","levelNo":1,"monthSummary":[],"myOrder":0,"nextLevelSaleAmount":1000,"nickName":"","operatingTime":1,"otherContacts":"","profit":0,"saleAmount":0,"salesTotalRebate":0,"storeLogo":"http://vpclub-img.oss-cn-shenzhen.aliyuncs.com/upload/100000058logo.png","storeMasterGuid":"00000000-0000-0000-0000-000000000000","storeMasterId":8784243,"storeName":"18688451605","storeStrategyUrl":"http://218.17.39.178:7007/html/storeStrategy.html","storeUrl":"http://218.17.39.178:7007/lnyp/ztewd/13765149.html","storesNumber":0,"taskTotalRebate":0,"themeId":0,"token":"5C382127BCD32C505C60B93F2D73887AAAB02A8F24F966A891613FD0F94B018C0489BDCEC53A2F57","total":0,"totalgem":0,"userOrgConfig":{"address":"","company_contacts":"","man":"","numcode":"","orgid":0,"orgname":"","orgtype":0,"phone":"","salesvolume":""},"weixinNum":""}
     * Message : 成功
     * ResultCode : 1000
     */

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }


    public static class DataBean {
        private int IdentityId;
        private String Wx_open_id;
        private double availableTotalRebate;
        private String bankBinded;
        private String defaultStoreBgImg;
        private double differNextLevelSaleAmount;
        private int flage;
        private int id;
        private int isShowPhoneNo;
        private String jpushGroupId;
        private String jpushid;
        private String levelName;
        private int levelNo;
        private int myOrder;
        private double nextLevelSaleAmount;
        private String nickName;
        private int operatingTime;
        private String otherContacts;
        private double profit;
        private double saleAmount;
        private double salesTotalRebate;
        private String storeLogo;
        private String storeMasterGuid;
        private int storeMasterId;
        private String storeName;
        private String storeStrategyUrl;
        private String storeUrl;
        private int storesNumber;
        private double taskTotalRebate;
        private int themeId;
        private String token;
        private int total;
        private int totalgem;
        /**
         * address :
         * company_contacts :
         * man :
         * numcode :
         * orgid : 0
         * orgname :
         * orgtype : 0
         * phone :
         * salesvolume :
         */

        private UserOrgConfigBean userOrgConfig;
        private String weixinNum;
        private List<?> monthSummary;

        public int getIdentityId() {
            return IdentityId;
        }

        public void setIdentityId(int IdentityId) {
            this.IdentityId = IdentityId;
        }

        public String getWx_open_id() {
            return Wx_open_id;
        }

        public void setWx_open_id(String Wx_open_id) {
            this.Wx_open_id = Wx_open_id;
        }

        public double getAvailableTotalRebate() {
            return availableTotalRebate;
        }

        public void setAvailableTotalRebate(double availableTotalRebate) {
            this.availableTotalRebate = availableTotalRebate;
        }

        public String getBankBinded() {
            return bankBinded;
        }

        public void setBankBinded(String bankBinded) {
            this.bankBinded = bankBinded;
        }

        public String getDefaultStoreBgImg() {
            return defaultStoreBgImg;
        }

        public void setDefaultStoreBgImg(String defaultStoreBgImg) {
            this.defaultStoreBgImg = defaultStoreBgImg;
        }

        public double getDifferNextLevelSaleAmount() {
            return differNextLevelSaleAmount;
        }

        public void setDifferNextLevelSaleAmount(double differNextLevelSaleAmount) {
            this.differNextLevelSaleAmount = differNextLevelSaleAmount;
        }

        public int getFlage() {
            return flage;
        }

        public void setFlage(int flage) {
            this.flage = flage;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsShowPhoneNo() {
            return isShowPhoneNo;
        }

        public void setIsShowPhoneNo(int isShowPhoneNo) {
            this.isShowPhoneNo = isShowPhoneNo;
        }

        public String getJpushGroupId() {
            return jpushGroupId;
        }

        public void setJpushGroupId(String jpushGroupId) {
            this.jpushGroupId = jpushGroupId;
        }

        public String getJpushid() {
            return jpushid;
        }

        public void setJpushid(String jpushid) {
            this.jpushid = jpushid;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public int getLevelNo() {
            return levelNo;
        }

        public void setLevelNo(int levelNo) {
            this.levelNo = levelNo;
        }

        public int getMyOrder() {
            return myOrder;
        }

        public void setMyOrder(int myOrder) {
            this.myOrder = myOrder;
        }

        public double getNextLevelSaleAmount() {
            return nextLevelSaleAmount;
        }

        public void setNextLevelSaleAmount(double nextLevelSaleAmount) {
            this.nextLevelSaleAmount = nextLevelSaleAmount;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getOperatingTime() {
            return operatingTime;
        }

        public void setOperatingTime(int operatingTime) {
            this.operatingTime = operatingTime;
        }

        public String getOtherContacts() {
            return otherContacts;
        }

        public void setOtherContacts(String otherContacts) {
            this.otherContacts = otherContacts;
        }

        public double getProfit() {
            return profit;
        }

        public void setProfit(double profit) {
            this.profit = profit;
        }

        public double getSaleAmount() {
            return saleAmount;
        }

        public void setSaleAmount(double saleAmount) {
            this.saleAmount = saleAmount;
        }

        public double getSalesTotalRebate() {
            return salesTotalRebate;
        }

        public void setSalesTotalRebate(double salesTotalRebate) {
            this.salesTotalRebate = salesTotalRebate;
        }

        public String getStoreLogo() {
            return storeLogo;
        }

        public void setStoreLogo(String storeLogo) {
            this.storeLogo = storeLogo;
        }

        public String getStoreMasterGuid() {
            return storeMasterGuid;
        }

        public void setStoreMasterGuid(String storeMasterGuid) {
            this.storeMasterGuid = storeMasterGuid;
        }

        public int getStoreMasterId() {
            return storeMasterId;
        }

        public void setStoreMasterId(int storeMasterId) {
            this.storeMasterId = storeMasterId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreStrategyUrl() {
            return storeStrategyUrl;
        }

        public void setStoreStrategyUrl(String storeStrategyUrl) {
            this.storeStrategyUrl = storeStrategyUrl;
        }

        public String getStoreUrl() {
            return storeUrl;
        }

        public void setStoreUrl(String storeUrl) {
            this.storeUrl = storeUrl;
        }

        public int getStoresNumber() {
            return storesNumber;
        }

        public void setStoresNumber(int storesNumber) {
            this.storesNumber = storesNumber;
        }

        public double getTaskTotalRebate() {
            return taskTotalRebate;
        }

        public void setTaskTotalRebate(double taskTotalRebate) {
            this.taskTotalRebate = taskTotalRebate;
        }

        public int getThemeId() {
            return themeId;
        }

        public void setThemeId(int themeId) {
            this.themeId = themeId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotalgem() {
            return totalgem;
        }

        public void setTotalgem(int totalgem) {
            this.totalgem = totalgem;
        }

        public UserOrgConfigBean getUserOrgConfig() {
            return userOrgConfig;
        }

        public void setUserOrgConfig(UserOrgConfigBean userOrgConfig) {
            this.userOrgConfig = userOrgConfig;
        }

        public String getWeixinNum() {
            return weixinNum;
        }

        public void setWeixinNum(String weixinNum) {
            this.weixinNum = weixinNum;
        }

        public List<?> getMonthSummary() {
            return monthSummary;
        }

        public void setMonthSummary(List<?> monthSummary) {
            this.monthSummary = monthSummary;
        }

        public static class UserOrgConfigBean {
            private String address;
            private String company_contacts;
            private String man;
            private String numcode;
            private int orgid;
            private String orgname;
            private int orgtype;
            private String phone;
            private String salesvolume;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getCompany_contacts() {
                return company_contacts;
            }

            public void setCompany_contacts(String company_contacts) {
                this.company_contacts = company_contacts;
            }

            public String getMan() {
                return man;
            }

            public void setMan(String man) {
                this.man = man;
            }

            public String getNumcode() {
                return numcode;
            }

            public void setNumcode(String numcode) {
                this.numcode = numcode;
            }

            public int getOrgid() {
                return orgid;
            }

            public void setOrgid(int orgid) {
                this.orgid = orgid;
            }

            public String getOrgname() {
                return orgname;
            }

            public void setOrgname(String orgname) {
                this.orgname = orgname;
            }

            public int getOrgtype() {
                return orgtype;
            }

            public void setOrgtype(int orgtype) {
                this.orgtype = orgtype;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getSalesvolume() {
                return salesvolume;
            }

            public void setSalesvolume(String salesvolume) {
                this.salesvolume = salesvolume;
            }
        }
    }
}
