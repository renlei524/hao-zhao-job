let vm = new Vue({
    el: "#rrapp",
    data() {
        /**
         * 验证是否为2-15个中文
         * @param {*} rule 规则
         * @param {*} value 输入的值
         * @param {*} callback 验证系统传入的回调
         */
        const validateChinese = function(rule, value, callback) {
            const reg = /^[\u4e00-\u9fa5]{2,15}$/;
            if (reg.test(value)) {
                callback();
            } else {
                callback(new Error("请输入15个字以内的汉字"));
            }
        };
        /**
         *验证手机号
         *
         * @param {*} rule
         * @param {*} value
         * @param {*} callback
         */
        const validatePhoneNumber = function(rule, value, callback) {
            const reg = /^1[0-9]{10}$/;
            if (reg.test(value)) {
                callback();
            } else {
                callback(new Error("请输入11位的手机号"));
            }
        };
        /**
         *验证邮箱
         *
         * @param {*} rule
         * @param {*} value
         * @param {*} callback
         */
        const validateEmail = function(rule, value, callback) {
            const reg = /\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/;
            if (reg.test(value)) {
                callback();
            } else {
                callback(new Error("请输入电子邮箱"));
            }
        };
        /**
         *验证银行结算账户
         *
         * @param {*} rule
         * @param {*} value
         * @param {*} callback
         */
        const validateBankActName = function(rule, value, callback) {
            if (value && value == vm.form.merchName) {
                callback();
            } else {
                callback(new Error("请输入结算账户"));
            }
        };
        /**
         *验证是否小于100
         *
         * @param {*} rule
         * @param {*} value
         * @param {*} callback
         */
        const validatePercent = function(rule, value, callback) {
            if (value && value < 100) {
                callback();
            } else {
                callback(new Error("请输入小于100的数"));
            }
        };
        return {
            form: {
                merchClass: "1", //商户性质
                merchType: "1", //商户类型
                relatedMerchName: "", //关联商户号
                merchName: "", //商户注册名称
                merchShortName: "", //商户简称
                userDefinedName: "", //小票名称
                regProvId: "", //注册地址所在省
                regCityId: "", //注册地址所在市
                regAreaId: "", //注册地址所在区
                provId: "", //经营地址所在省
                cityId: "", //经营地址所在市
                areaId: "", //经营地址所在区
                regAddr: "", //商户注册详细地址
                merchAddr: "", //商户经营详细地址
                businessShours: "00:00", //营业开始时间
                businessEhours: "23:50", //营业结束时间
                merHeader: "", //商户所属总部
                isCreditCode: "1", //是否三证合一
                creditCode: "", //统一社会信用代码证
                regCode: "", //营业执照编号
                licType: "1", //证照有效期
                licSdate: "", //证照开始日期
                licEdate: "", //证照结束日期
                orgCode: "", //组织机构代码证
                cateType: "1", //商户经营类型
                legalName: "", //法人
                idType: "", //法人证件类型
                idNo: "", //法人证件号码
                idValidType: "1", //法人证件有效期
                idSdate: "", //法人证件有效期开始日期
                idEdate: "", //法人证件有效期证件结束日期
                contactName: "", //商户联系人
                contactIdType: "", //联系人证件类型
                contactIdNo: "", //联系人证件号码
                contactIdValidType: "1", //联系人证件有效期
                contactIdSdate: "", //联系人证件有效期开始日期
                contactIdEdate: "", //联系人证件有效期结束日期
                contactTelno: "", //联系人手机
                contactEmail: "", //联系人邮箱
                contractNum: "", //合同编号
                signName: "", //签约业务经理
                signDate: "", //签约日期
                signSdate: "", //合同有效期开始日期
                signEdate: "", //合同有效期结束日期
                tellerId: "", //管理员账号
                isSendMes: "1", //是否发送短信通知商户
                isPrivate: "0", //结算账户类型
                bankActName: "", //结算账户名称
                bankActId: "", //结算账号
                bankProvId: "", //银行所在省
                bankCityId: "", //银行所在市
                bankAreaId: "", //银行所在区县
                bankName: "", //银行名称
                settleTerm: "2", //结算周期
                fee01: "", //D+1结算手续费（%）
                accountIdType: "", //持卡人证件类型
                accountIdNo: "", //持卡人证件号码
                accountIdValidType: "1", //证件有效期
                accountIdSdate: "", //证件有效期开始日期
                accountIdEdate: "", //证件有效期结束日期
                auth24: "0", //是否开通TS业务
                fee20: "", //TS结算手续费（固定）
                fee21: "", //TS结算手续费（%）
                fee22: "", //封顶值
                tsCashLimit: "", //TS起结金额（元）
                auth23: "1", //是否开通银行卡支付
                chargeCateCode: "01", //手续费类型
                fee02: 0, //借记卡手续费（%）
                fee04: 0, //封顶
                fee03: 0, //贷记卡手续费（%）
                unionRegCode: "", //银联现场注册码
                fee05: 0, //云闪付借记卡手续费（%）
                fee07: 0, //封顶(元)
                fee06: 0, //云闪付贷记卡手续费（%）
                auth20: "0", //开通银联二维码
                fee23: 0, //借记卡手续费（%）
                fee24: 0, //封顶(元)
                fee25: 0, //贷记卡手续费（%）
                auth18: "0", //是否开通微信支付
                wechatCateCode: "", //商户经营类目（微信）
                fee08: 0, //微信支付手续费
                wechatPubNumAppid: "", //公众号支付appid
                wechatPubNumAuth: "", //公众号支付授权目录
                wechatPubNum: "", //关注公众号
                isMerWechat: "1", //是否为商户的公众号
                auth19: "0", //是否开通支付宝支付
                alipayCateCode: "", //商户经营类目（支付宝）
                fee12: 0, //支付宝手续费
                zipName: "", //证照图片压缩包文件名
                archFlph01: "", //营业执照图片
                archFlph02: "", //税务登记证
                archFlph03: "", //组织机构代码证
                archFlph04: "", //开户许可证
                archFlph05: "", //法人身份证正面
                archFlph06: "", //法人身份证反面
                archFlph07: "", //结算人身份证正面
                archFlph08: "", //结算人身份证反面
                archFlph09: "", //商务协议
                archFlph10: "", //公司照片一
                archFlph11: "", //公司照片二
                archFlph12: "", //公司照片三
                archFlph13: "", //联系人身份证正面
                archFlph14: "", //联系人身份证反面
                archFlph15: "", //店铺门头照片
                archFlph16: "", //店铺收银台照片
                archFlph17: "", //店内照片
                archFlph18: "", //结算卡正面
                archFlph19: "", //结算卡反面
                memberId: "", //代理商会员号
                opTellerId: "", //代理商操作员
                bgRetUrl: "", //异步通知地址
                reqSerialNum: "", //请求流水号
                wxAppid: "", //APP支付appId
                pnrpayMerType: "", //商户种类
                picUploadWay: "01", //图片上传方式
                pictureIDs: "", //图片文件ID
                auth29: "" //是否开通延迟入账
            },
            regProvinceCode: "0",
            regCityCode: "0",
            regAreaCode: "0",
            provinceCode: "0",
            cityCode: "0",
            areaCode: "0",
            feedType: "0",
            provinces: [],
            regProvinces: [],
            cities: [],
            regCities: [],
            areas: [],
            regAreas: [],
            bankProvinces: [],
            bankCities: [],
            bankAreas: [],
            title: "",
            rules: {
                merchClass: [
                    {
                        required: true,
                        message: "请输入商户性质",
                        trigger: "blur"
                    }
                ],
                merchType: [
                    {
                        required: true,
                        message: "请输入商户类型",
                        trigger: "blur"
                    }
                ],

                relatedMerchName: [
                    {
                        required: true,
                        message: "请输入关联商户号",
                        trigger: "blur"
                    }
                ],
                merchName: [
                    {
                        required: true,
                        message: "请输入商户注册名称",
                        trigger: "blur"
                    }
                ],
                merchShortName: [
                    {
                        required: true,
                        message: "请输入商户简称",
                        trigger: "blur"
                    }
                ],
                userDefinedName: [
                    {
                        required: true,
                        message: "请输入小票名称",
                        trigger: "blur"
                    }
                ],
                regProvId: [
                    {
                        required: true,
                        message: "请输入注册地址所在省",
                        trigger: "change"
                    }
                ],
                regCityId: [
                    {
                        required: true,
                        message: "请输入注册地址所在市",
                        trigger: "change"
                    }
                ],
                regAreaId: [
                    {
                        required: true,
                        message: "请输入注册地址所在区",
                        trigger: "change"
                    }
                ],
                regAddr: [
                    {
                        required: true,
                        message: "请输入注册详细地址",
                        trigger: "blur"
                    }
                ],
                provId: [
                    {
                        required: true,
                        message: "请输入经营地址所在省",
                        trigger: "change"
                    }
                ],
                cityId: [
                    {
                        required: true,
                        message: "请输入经营地址所在市",
                        trigger: "change"
                    }
                ],
                areaId: [
                    {
                        required: true,
                        message: "请输入经营地址所在区县",
                        trigger: "change"
                    }
                ],
                merchAddr: [
                    {
                        required: true,
                        message: "请输入经营详细地址",
                        trigger: "blur"
                    }
                ],
                businessShours: [
                    {
                        required: true,
                        message: "请选择营业开始时间",
                        trigger: "blur"
                    }
                ],
                businessEhours: [
                    {
                        required: true,
                        message: "营业结束时间",
                        trigger: "blur"
                    }
                ],
                isCreditCode: [
                    {
                        required: true,
                        message: "请选择是否三证合一",
                        trigger: "blur"
                    }
                ],
                creditCode: [
                    {
                        required: true,
                        message: "请输入统一社会信用代码证",
                        trigger: "blur"
                    }
                ],
                regCode: [
                    {
                        required: false,
                        message: "请输入营业执照编号",
                        trigger: "blur"
                    }
                ],
                licType: [
                    {
                        required: true,
                        message: "请选择证照有效期",
                        trigger: "blur"
                    }
                ],
                licSdate: [
                    {
                        required: true,
                        message: "请输入证照开始日期",
                        trigger: "blur"
                    }
                ],
                licEdate: [
                    {
                        required: true,
                        message: "请输入证照结束日期",
                        trigger: "blur"
                    }
                ],
                orgCode: [
                    {
                        required: false,
                        message: "请输入组织机构代码证",
                        trigger: "blur"
                    }
                ],
                cateType: [
                    {
                        required: true,
                        message: "请选择商户经营类型",
                        trigger: "blur"
                    }
                ],
                legalName: [
                    {
                        validator: validateChinese,
                        trigger: "blur",
                        required: true
                    }
                ],
                idType: [
                    {
                        required: true,
                        message: "请选择法人证件类型",
                        trigger: "change"
                    }
                ],
                idNo: [
                    {
                        required: true,
                        message: "请输入法人证件号码",
                        trigger: "blur"
                    }
                ],
                idValidType: [
                    {
                        required: true,
                        message: "请选择法人证件有效期",
                        trigger: "blur"
                    }
                ],
                idSdate: [
                    {
                        required: true,
                        message: "请选择法人证件有效期开始日期",
                        trigger: "blur"
                    }
                ],
                idEdate: [
                    {
                        required: true,
                        message: "请输入法人证件有效期证件结束日期",
                        trigger: "blur"
                    }
                ],
                contactName: [
                    {
                        validator: validateChinese,
                        trigger: "blur",
                        required: true
                    }
                ],
                contactIdType: [
                    {
                        required: true,
                        message: "请选择联系人证件类型",
                        trigger: "change"
                    }
                ],
                contactIdNo: [
                    {
                        required: true,
                        message: "请选择联系人证件号码",
                        trigger: "blur"
                    }
                ],
                contactIdValidType: [
                    {
                        required: true,
                        message: "请选择联系人证件有效期",
                        trigger: "blur"
                    }
                ],
                contactIdSdate: [
                    {
                        required: true,
                        message: "请选择联系人证件有效期开始日期",
                        trigger: "blur"
                    }
                ],
                contactIdEdate: [
                    {
                        required: true,
                        message: "联系人证件有效期结束日期",
                        trigger: "blur"
                    }
                ],
                contactTelno: [
                    {
                        required: true,
                        validator: validatePhoneNumber,
                        trigger: "change"
                    }
                ],
                contactEmail: [
                    {
                        validator: validateEmail,
                        trigger: "change",
                        required: true
                    }
                ],
                contractNum: [
                    {
                        required: true,
                        message: "请输入合同编号",
                        trigger: "blur"
                    }
                ],
                signName: [
                    {
                        required: true,
                        message: "请输入签约业务经理",
                        trigger: "blur"
                    }
                ],
                signDate: [
                    {
                        required: true,
                        message: "请输入签约日期",
                        trigger: "blur"
                    }
                ],
                signSdate: [
                    {
                        required: true,
                        message: "请输入合同有效期开始日期",
                        trigger: "blur"
                    }
                ],
                signEdate: [
                    {
                        required: true,
                        message: "请输入合同有效期结束日期",
                        trigger: "blur"
                    }
                ],
                tellerId: [
                    {
                        required: true,
                        message: "请输入管理员账号",
                        trigger: "blur"
                    }
                ],
                isSendMes: [
                    {
                        required: true,
                        message: "请选择是否发送短信通知商户",
                        trigger: "blur"
                    }
                ],
                isPrivate: [
                    {
                        required: true,
                        message: "请选择结算账户类型",
                        trigger: "blur"
                    }
                ],
                bankActName: [
                    {
                        validator: validateBankActName,
                        required: true,
                        message: "请输入结算账户名",
                        trigger: "blur"
                    }
                ],
                bankActId: [
                    {
                        required: true,
                        message: "请输入结算账号",
                        trigger: "blur",
                    }
                ],
                bankProvId: [
                    {
                        required: true,
                        message: "请输入银行所在省",
                        trigger: "blur"
                    }
                ],
                bankCityId: [
                    {
                        required: true,
                        message: "请输入银行所在市",
                        trigger: "blur"
                    }
                ],
                bankAreaId: [
                    {
                        required: true,
                        message: "请输入银行所在区县",
                        trigger: "blur"
                    }
                ],
                bankName: [
                    {
                        required: true,
                        message: "请输入银行名称",
                        trigger: "blur"
                    }
                ],
                settleTerm: [
                    {
                        required: true,
                        message: "请选择结算周期",
                        trigger: "blur"
                    }
                ],
                fee01: [
                    {
                        validator: validatePercent,
                        required: true,
                        trigger: "blur"
                    }
                ],
                accountIdType: [
                    {
                        required: true,
                        message: "请选择持卡人证件类型",
                        trigger: "change"
                    }
                ],
                accountIdNo: [
                    {
                        required: true,
                        message: "请输入持卡人证件号码",
                        trigger: "blur"
                    }
                ],
                accountIdValidType: [
                    {
                        required: true,
                        message: "请选择证件有效期",
                        trigger: "blur"
                    }
                ],
                accountIdSdate: [
                    {
                        required: true,
                        message: "请选择证件有效期开始日期",
                        trigger: "blur"
                    }
                ],
                accountIdEdate: [
                    {
                        required: true,
                        message: "请选择证件有效期结束日期",
                        trigger: "blur"
                    }
                ],
                auth24: [
                    {
                        required: true,
                        message: "请选择是否开通TS业务",
                        trigger: "blur"
                    }
                ],
                fee20: [
                    {
                        required: true,
                        message: "TS结算手续费（固定）",
                        trigger: "blur"
                    }
                ],
                fee21: [
                    {
                        validator: validatePercent,
                        required: true,
                        trigger: "blur"
                    }
                ],
                fee22: [
                    {
                        required: true,
                        message: "请输入封顶值",
                        trigger: "blur"
                    }
                ],
                tsCashLimit: [
                    {
                        required: true,
                        message: "请输入TS起结金额（元）",
                        trigger: "blur"
                    }
                ],
                auth23: [
                    {
                        required: true,
                        message: "请选择是否开通银行卡支付",
                        trigger: "blur"
                    }
                ],
                chargeCateCode: [
                    {
                        required: true,
                        message: "请选择手续费类型",
                        trigger: "blur"
                    }
                ],
                fee02: [
                    {
                        validator: validatePercent,
                        required: true,
                        trigger: "blur"
                    }
                ],
                fee04: [
                    {
                        required: true,
                        message: "请输入封顶值",
                        trigger: "blur"
                    }
                ],
                fee03: [
                    {
                        validator: validatePercent,
                        required: true,
                        trigger: "blur"
                    }
                ],
                fee05: [
                    {
                        validator: validatePercent,
                        required: true,
                        trigger: "blur"
                    }
                ],
                fee06: [
                    {
                        validator: validatePercent,
                        required: true,
                        trigger: "blur"
                    }
                ],
                auth20: [
                    {
                        required: false,
                        message: "请选择是否开通银联二维码",
                        trigger: "blur"
                    }
                ],
                fee23: [
                    {
                        validator: validatePercent,
                        required: false,
                        trigger: "blur"
                    }
                ],
                fee24: [
                    {
                        required: false,
                        message: "请输入封顶(元)",
                        trigger: "blur"
                    }
                ],
                fee25: [
                    {
                        required: false,
                        message: "请输入贷记卡手续费",
                        trigger: "blur"
                    }
                ],
                auth18: [
                    {
                        required: true,
                        message: "请选择是否开通微信支付",
                        trigger: "blur"
                    }
                ],
                wechatCateCode: [
                    {
                        required: false,
                        message: "请输入商户经营类目（微信）",
                        trigger: "blur"
                    }
                ],
                fee08: [
                    {
                        required: false,
                        message: "请输入微信支付手续费",
                        trigger: "blur"
                    }
                ],
                wechatPubNumAppid: [
                    {
                        required: false,
                        message: "请输入公众号支付appid",
                        trigger: "blur"
                    }
                ],
                wechatPubNumAuth: [
                    {
                        required: false,
                        message: "请输入公众号支付授权目录",
                        trigger: "blur"
                    }
                ],
                isMerWechat: [
                    {
                        required: true,
                        message: "请选择是否为商户的公众号",
                        trigger: "blur"
                    }
                ],
                auth19: [
                    {
                        required: true,
                        message: "请选择是否开通支付宝支付",
                        trigger: "blur"
                    }
                ],
                alipayCateCode: [
                    {
                        required: false,
                        message: "请输入商户经营类目（支付宝）",
                        trigger: "blur"
                    }
                ],
                fee12: [
                    {
                        required: false,
                        message: "请输入支付宝手续费",
                        trigger: "blur"
                    }
                ],
                memberId: [
                    {
                        required: true,
                        message: "请输入代理商会员号",
                        trigger: "blur",
                        min: 18,
                        max: 18
                    }
                ],
                opTellerId: [
                    {
                        required: true,
                        message: "请输入代理商操作员",
                        trigger: "blur"
                    }
                ],
                pnrpayMerType: [
                    {
                        required: true,
                        message: "请选择商户种类",
                        trigger: "change"
                    }
                ]
            },
            //证件类型
            IDCardTypes: [
                {
                    label: "身份证",
                    typeId: "01"
                },
                {
                    label: "护照",
                    typeId: "02"
                },
                {
                    label: "港澳台通行证",
                    typeId: "03"
                },
                {
                    label: "军官证",
                    typeId: "04"
                },
                {
                    label: "回乡证",
                    typeId: "05"
                },
                {
                    label: "工商登记号",
                    typeId: "06"
                },
                {
                    label: "其他",
                    typeId: "09"
                }
            ],
            // 企业类型
            pnrpayMerTypes: [
                {
                    label: "政府机构",
                    typeId: "1"
                },
                {
                    label: "国营企业",
                    typeId: "2"
                },
                {
                    label: "私营企业",
                    typeId: "3"
                },
                {
                    label: "外资企业",
                    typeId: "4"
                },
                {
                    label: "个体工商户",
                    typeId: "5"
                },
                {
                    label: "事业单位",
                    typeId: "7"
                }
            ]
        };
    },
    mounted: function() {
        this.getProvince();
    },
    methods: {
        /**
         *省市改变时，发送请求
         * @param {*} name
         * @param {*} addrType
         * @param {*} method
         * @param {*} num 注册地：1，经营地：2，银行所在地：3
         */
        handleAddrChange(name,addrType,method,num) {
            let objArr = this[addrType].filter(item => {
                return item.name == name;
            });
            this[method](objArr[0].areaCode, num);
        },
        getProvince() {
            let url = "sys/twbarea/infoList/0";
            axios({
                method: "post",
                url: baseURL + url,
                contentType: "application/json"
            }).then(resp => {
                this.provinces = [...resp.data];
                this.regProvinces = [...resp.data];
                this.bankProvinces = [...resp.data];
            });
        },
        /**
         *根据省份，请求城市
         *
         * @param {*} provinceId 省id
         * @param {*} addrType 注册地：1，经营地：2，银行所在地：3
         */
        getCities(provinceId, addrType) {
            let url = "sys/twbarea/infoList/" + provinceId;
            data = "";
            axios({
                method: "post",
                url: baseURL + url,
                contentType: "application/json"
            }).then(resp => {
                if (addrType == 1) {
                    this.regCities = [...resp.data];
                } else if (addrType == 2) {
                    this.cities = [...resp.data];
                } else {
                    this.bankCities = [...resp.data];
                }
            });
        },
        getArea(cityId, addrType) {
            let url = "sys/twbarea/infoList/" + cityId;
            data = "";
            axios({
                method: "post",
                url: baseURL + url,
                contentType: "application/json"
            }).then(resp => {
                if (addrType == 1) {
                    this.regAreas = [...resp.data];
                } else if (addrType == 2) {
                    this.areas = [...resp.data];
                } else {
                    this.bankAreas = [...resp.data];
                }
            });
        },
        /**
         * 保留位数
         * @param {*} fee 费用项
         * @param {*} num 保留位数
         */
        fixFee(fee, num) {
            if (this.$refs[fee].value) {
                if (isNaN(parseFloat(this.$refs[fee].value))) {
                    this.form[fee] = 0;
                } else {
                    this.form[fee] = parseFloat(this.$refs[fee].value).toFixed(
                        num
                    );
                }
            } else {
                this.form[fee] = 0;
            }
        },
        /**
         *
         * 提交表单
         * @param {*} formName 表单名
         */
        submitForm(formName) {
            this.$refs[formName].validate(valid => {
                const url = "/nsposmweb/webB1412";
                axios({
                    method: "post",
                    url: baseURL + url,
                    data: {
                        checkValue: "",
                        jsonData: { ...this.form }
                    },
                    contentType: "application/json"
                }).then(resp => {
                    console.log(resp);
                });

                // if (valid) {
                //     alert("submit!");
                // } else {
                //     console.log("error submit!!");
                //     return false;
                // }
            });
        }
    },
    watch: {
        "form.merchClass": function(newMerchClass) {
            //1.企业商户，2.个体商户
            if (newMerchClass == "1") {
                this.rules.regProvId[0].required = false;
                this.rules.regCityId[0].required = false;
                this.rules.regAreaId[0].required = false;
                this.rules.idType[0].required = true;
                this.rules.idNo[0].required = true;
                this.rules.idValidType[0].required = true;
                this.rules.idSdate[0].required = true;
            } else {
                this.rules.regProvId[0].required = true;
                this.rules.regCityId[0].required = true;
                this.rules.regAreaId[0].required = true;
                this.rules.idType[0].required = false;
                this.rules.idValidType[0].required = false;
                this.rules.idSdate[0].required = false;

                this.form.regProvId = "";
                this.form.regCityId = "";
                this.form.regAreaId = "";
            }
        },
        "form.merchType": function(newMerchType) {
            //单店时不填
            if (newMerchType == "2") {
                this.form.relatedMerchName = "";
                this.rules.relatedMerchName[0].required = false;
            } else {
                this.rules.relatedMerchName[0].required = true;
            }
        },
        "form.merchShortName": function(newMerchShortName) {
            this.form.userDefinedName = newMerchShortName;
        },
        "form.isCreditCode": function(newIsCreditCode) {
            if (newIsCreditCode == "1") {
                this.form.regCode = "";
                this.rules.regCode[0].required = false;
                this.rules.creditCode[0].required = true;
            } else {
                this.form.creditCode = "";
                this.rules.regCode[0].required = true;
                this.rules.creditCode[0].required = false;
            }
        },
        //法人证件有效期类型1.非长期，2.长期
        "form.idValidType": function(newIdValidType) {
            if (newIdValidType == "1") {
                this.rules.idEdate[0].required = true;
            } else {
                this.rules.idEdate[0].required = false;
            }
        },
        //联系人证件有效期1.非长期，2.长期
        "form.contactIdValidType": function(newType) {
            if (newType == "1") {
                this.rules.contactIdEdate[0].required = true;
            } else {
                this.rules.contactIdEdate[0].required = false;
            }
        },
        //选项：1代表T+1；2代表D+1
        "form.settleTerm": function(newType) {
            if (newType == "1") {
                this.rules.fee01[0].required = false;
            } else {
                this.rules.fee01[0].required = true;
            }
        },
        //选项：0.对公 1.对私
        "form.isPrivate": function(newType) {
            if (newType == "1") {
                this.rules.accountIdType[0].required = true;
                this.rules.accountIdValidType[0].required = true;
                this.rules.accountIdSdate[0].required = true;
            } else {
                this.rules.accountIdType[0].required = false;
                this.rules.accountIdValidType[0].required = false;
                this.rules.accountIdSdate[0].required = false;
            }
        },
        //选项：1，非长期；2，长期
        "form.accountIdValidType": function(newType) {
            if (newType == "1") {
                this.rules.accountIdEdate[0].required = true;
            } else {
                this.rules.accountIdEdate[0].required = false;
            }
        },
        //选项：0：否 1：是
        "form.auth24": function(newType) {
            if (newType == "1" && this.feedType == "0") {
                this.rules.fee20[0].required = true;
            } else if (newType == "1" && this.feedType == "1") {
                this.rules.fee21[0].required = true;
                this.rules.fee22[0].required = true;
            } else {
                this.rules.fee20[0].required = false;
                this.rules.fee22[0].required = false;
                this.rules.fee21[0].required = false;
            }
            if (newType == "1") {
                this.rules.tsCashLimit[0].required = true;
            } else {
                this.rules.tsCashLimit[0].required = false;
            }
        },
        //选项：0 固定 1百分百
        feedType: function(newType) {
            if (newType == "1" && this.form.auth24 == "1") {
                this.rules.fee21[0].required = true;
                this.rules.fee22[0].required = true;
            } else if (newType == "0" && this.form.auth24 == "1") {
                this.rules.fee20[0].required = true;
            } else {
                this.rules.fee20[0].required = false;
                this.rules.fee21[0].required = false;
                this.rules.fee22[0].required = false;
            }
        },
        //是否开通银行卡支付 选项：0：否 1：是
        "form.auth23": function(newType) {
            if (newType == "1") {
                this.rules.chargeCateCode[0].required = true;
                this.rules.fee02[0].required = true;
                this.rules.fee03[0].required = true;
                this.rules.fee04[0].required = true;
                this.rules.fee05[0].required = true;
                this.rules.fee06[0].required = true;
            } else {
                this.rules.chargeCateCode[0].required = false;
                this.rules.fee02[0].required = false;
                this.rules.fee03[0].required = false;
                this.rules.fee04[0].required = false;
                this.rules.fee05[0].required = false;
                this.rules.fee06[0].required = false;
                this.form.fee02 = 0;
                this.form.fee03 = 0;
                this.form.fee04 = 0;
                this.form.fee05 = 0;
                this.form.fee06 = 0;
                this.form.fee07 = 0;
            }
        },
        //手续费类型 选项：01-减免类 02-优惠类 03-标准类
        "form.chargeCateCode": function(newType) {
            if (newType == "01") {
                this.form.fee02 = 0;
                this.form.fee03 = 0;
                this.form.fee04 = 0;
                this.form.fee05 = 0;
                this.form.fee06 = 0;
                this.form.fee07 = 0;
            }
        },
        //开通银联二维码 选项：0：否 1：是
        "form.auth20": function(newType) {
            if (newType == "1") {
                this.rules.fee23[0].required = true;
                this.rules.fee24[0].required = true;
                this.rules.fee25[0].required = true;
            } else {
                this.rules.fee23[0].required = false;
                this.rules.fee24[0].required = false;
                this.rules.fee25[0].required = false;
                this.form.fee23 = 0;
                this.form.fee24 = 0;
                this.form.fee25 = 0;
            }
        },
        //开通微信支付 选项：0：否 1：是
        "form.auth18": function(newType) {
            if (newType == "1") {
                this.rules.wechatCateCode[0].required = true;
                this.rules.fee08[0].required = true;
            } else {
                this.rules.wechatCateCode[0].required = false;
                this.rules.fee08[0].required = false;
                this.form.fee08 = 0;
            }
        },
        // 微信公众号
        "form.wechatPubNum": function(newType) {
            if (newType) {
                this.rules.wechatPubNumAppid[0].required = true;
                this.rules.wechatPubNumAuth[0].required = true;
            } else {
                this.rules.wechatPubNumAppid[0].required = false;
                this.rules.wechatPubNumAuth[0].required = false;
            }
        },
        //开通支付宝 选项：0：否 1：是
        "form.auth19": function(newType) {
            if (newType == "1") {
                this.rules.alipayCateCode[0].required = true;
                this.rules.fee12[0].required = true;
            } else {
                this.rules.alipayCateCode[0].required = false;
                this.rules.fee12[0].required = false;
                this.form.alipayCateCode = "";
                this.form.fee12 = 0;
            }
        }
    }
});
