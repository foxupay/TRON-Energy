<template>
    <div style="background: #e7f0f9;min-height: 100%;">
        <myheader ref="gaibian" class="gongyong"></myheader>
        <div style="height:60px ;width: 100%;"></div>

        <div class="y1">
            <div class="y2">
                <span class="yu1">余额：</span>
                <span class="yu2">{{yue}}</span>
                <span>TRX</span>

            </div>
            <div style="margin-left:20px ;">
                <el-button type="primary" @click="gochognzhi">充值</el-button>
            </div>
        </div>

        <div class="d1">
            <div style="background:#fff ;padding: 10px;font-weight: 550;">余额明细</div>
            <el-table :data="postList">

                <el-table-column label="订单金额" align="center" prop="status">
                    <template slot-scope="scope">

                        {{ scope.row.amount}}TRX

                        <!-- <el-tag type="danger">{{ scope.row | zhuangtai}}</el-tag> -->

                    </template>
                </el-table-column>
                <el-table-column label="余额变更前" align="center" prop="status">
                    <template slot-scope="scope">
                        {{scope.row.beforeBalance}}TRX

                    </template>
                </el-table-column>
                <el-table-column label="余额变更后" align="center">
                    <template slot-scope="scope">
                        {{scope.row.afterBalance }}TRX
                    </template>
                </el-table-column>
                <el-table-column label="订单编号" align="center">
                    <template slot-scope="scope">
                        {{ scope.row.orderNo}}
                    </template>
                </el-table-column>
                <el-table-column label="操作时间" align="center">
                    <template slot-scope="scope">
                        {{ scope.row.createAt}}
                    </template>
                </el-table-column>





            </el-table>

        </div>


        <div class="d2">
            <div class="d3" v-for="item in postList">
                <div class="d4">
                    <div class="d5">订单金额</div>
                    <div class="d6" style="color:#ff8d1a ;">{{ item.amount}}TRX</div>
                </div>
                <div class="d4">
                    <div class="d5">余额变更前</div>
                    <div class="d6" style="color: #ff8d1a;">{{item.beforeBalance}}TRX</div>
                </div>
                <div class="d4">
                    <div class="d5">余额变更后</div>
                    <div class="d6" style="color: #ff8d1a;">{{ item.afterBalance}}TRX</div>
                </div>

                <div class="d4">
                    <div class="d5">订单编号</div>
                    <div class="d6">{{item.orderNo}}</div>
                </div>
                <div class="d4">
                    <div class="d5">操作时间</div>
                    <div class="d6">{{item.createAt}}</div>
                </div>
            </div>

        </div>

        <!-- <div>
            <el-pagination class="d8" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="currentPage" :page-sizes="[10, 20, 50, 100]" :page-size="10" :pager-count="3"
                layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div> -->
        <div class="d7">
            <el-pagination background layout="prev, pager, next" :total="total" :pager-count="5"
                @size-change="handleSizeChange" @current-change="handleCurrentChange">
            </el-pagination>
        </div>



        <el-dialog title="充值" :visible.sync="centerDialogVisibledz" :width="dzkuan" center @close="guanbizf">
            <div style="padding: 10px;border-radius: 10px;border: 1px solid #1f87ff;">
                <div class="e27">
                    1USDT ≈ {{huilv}} TRX
                </div>
                <div class="e28">
                    <div id="qrcode" ref="qrcode"></div>
                </div>


                <div
                    style="display:flex ;justify-content: center;align-items: center;background:#f2f8ff ;padding: 5px 0;">


                    <div id="copyTarget1" style="margin-left: 5px;">{{rechargeAddress}}</div>
                    <i class="el-icon-document-copy" id="copyBtn1"
                        style="font-size:16px ;color: #1787ff;margin-left: 10px;" :data-clipboard-text="rechargeAddress"
                        data-clipboard-action="copy" data-clipboard-target="#copyTarget1" @click="copy1"></i>
                </div>
                <div class="e26">
                    *请使用波场浏览器或手机钱包进行转账。
                </div>
                <div class="e26">
                    *请核对钱包地址后再转账,若转账后没查到充值金额,可联系客服找回。
                </div>
                <div class="e26">
                    *转账金额必须大于1TRX。
                </div>
                <div class="e26">
                    *当前地址仅支持TRX或USDT(TRC20)转账。
                </div>



            </div>


            <span slot="footer" class="dialog-footer">


            </span>
        </el-dialog>



    </div>
</template>
<script>
    import QRCode from 'qrcodejs2'
    import Clipboard from "clipboard";
    import myheader from "@/components/gongyong"
    import qs from "qs"
    export default {
        data() {
            return {
                windowWidth: document.body.clientWidth,
                loading: true,

                // 表格数据
                // 总条数
                total: 0,
                // 表格数据
                postList: [],
                // 查询参数

                pageNum: 1,
                pageSize: 10,
                currentPage: 0,
                yue: '',
                centerDialogVisibledz: false,
                huilv: '',

                dzkuan: '90%',
                rechargeAddress: '',


            }
        },
        components: {
            myheader

        },
        watch: {

            // 监听页面宽度
            windowWidth(val) {
                if (val > 1200) {

                    this.dzkuan = '30%'

                } else if (val > 699 && val <= 1200) {

                    this.dzkuan = '50%'
                } else {


                    this.dzkuan = '90%'
                }

                // console.log('实时屏幕宽度：', val, this.windowHeight)
            }

        },
        methods: {
            guanbizf() {
                this.centerDialogVisibledz = false
            },
            //打开充值
            gochognzhi() {
                // var params = {
                //     'pageNum': this.pageNum,
                //     'pageSize': this.pageSize
                // }
                this.$axios({
                    method: 'GET',
                    url: '/api/app/lease/rate',
                    // params: params,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization': this.getCookie('nltoken')
                    }
                }).then(userData => {
                    if (userData.data.code == "200") {
                        this.huilv = userData.data.data.rate
                        this.centerDialogVisibledz = true

                        setTimeout(() => {
                            document.getElementById('qrcode').innerHTML = "";
                            let qrcode = new QRCode(this.$refs.qrcode, {
                                width: 200, // 二维码宽度，单位像素
                                height: 200, // 二维码高度，单位像素
                                text: this.rechargeAddress  // 生成二维码的链接
                            })
                        }, 500)


                    } else {
                        this.loading = false
                        this.$message.error(userData.data.msg);
                    }
                }).catch(error => {
                    console.log(error)
                })
            },
            handleSizeChange(val) {
                this.pageNum = 1
                this.pageSize = val
                this.getList()
            },
            handleCurrentChange(val) {
                this.pageNum = val
                this.getList()
            },
            //打开hash
            handleUpdate(url) {
                var tt = 'https://tronscan.org/#/transaction/' + url
                window.open(tt)
                clearInterval(this.timer);
                this.timer = null; 0
            },

            //复制
            copy() {
                let clipboard = new Clipboard('#copyBtn');
                clipboard.on('success', e => {
                    this.$message({
                        message: '复制成功',
                        type: 'success'
                    });

                    clipboard.destroy()
                })
                clipboard.on('error', e => {
                    this.$message({
                        message: '复制失败',
                        type: 'warning'
                    });
                    clipboard.destroy()
                })
            },
            copy1() {
                let clipboard = new Clipboard('#copyBtn1');
                clipboard.on('success', e => {
                    this.$message({
                        message: '复制成功',
                        type: 'success'
                    });

                    clipboard.destroy()
                })
                clipboard.on('error', e => {
                    this.$message({
                        message: '复制失败',
                        type: 'warning'
                    });
                    clipboard.destroy()
                })
            },






            getList() {
                this.loading = true
                var params = qs.stringify({
                    'pageNum': this.pageNum,
                    'pageSize': this.pageSize
                })

                this.$axios.post('/api/app/user/info/balance/log', params, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization': this.getCookie('nltoken')
                    }
                }).then(userData => {
                    if (userData.data.code == "200") {
                        this.loading = false
                        this.postList = userData.data.rows
                        this.total = userData.data.total

                    } else {
                        this.loading = false
                        this.$message.error(userData.data.msg);
                    }
                }).catch(error => {
                    console.log(error)
                })
            },
            getyue() {
                // var params = qs.stringify({
                //     'account': this.ruleForm.account,
                //     'code': this.ruleForm.code,
                // })

                this.$axios.post('/api/app/user/info/balance', null, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization': this.getCookie('nltoken')
                    }
                }).then(userData => {

                    if (userData.data.code == '200') {
                        this.yue = userData.data.data.balance
                    }
                    else {
                        this.loading = false
                        this.$message.error(userData.data.msg);
                    }
                }).catch(error => {
                    console.log(error)
                })
            },
            getzhanghu() {
                // var params = qs.stringify({
                //     'account': this.ruleForm.account,
                //     'code': this.ruleForm.code,
                // })

                this.$axios.post('/api/app/user/info', null, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization': this.getCookie('nltoken')
                    }
                }).then(userData => {

                    if (userData.data.code == '200') {
                        this.rechargeAddress = userData.data.data.rechargeAddress
                    }
                    else {
                        this.loading = false
                        this.$message.error(userData.data.msg);
                    }
                }).catch(error => {
                    console.log(error)
                })
            },
            jianting() {
                var val = this.windowWidth
                if (val > 1200) {

                    this.dzkuan = '30%'

                } else if (val > 699 && val <= 1200) {

                    this.dzkuan = '50%'
                } else {


                    this.dzkuan = '90%'
                }



            },



        },

        // beforeDestroy() {
        //     clearInterval(this.timer);
        //     this.timer = null;
        // },
        mounted() {
            this.$refs.gaibian.genggai('5')
            window.onresize = () => {
                return (() => {

                    this.windowWidth = document.body.clientWidth // 宽
                })()
            }




        },
        created: function () {
            this.jianting()
            this.getyue()
            this.getList()
            this.getzhanghu()



        },
        filters: {
            time: function (data) {
                if (data.leaseDurationType == 1) {
                    return data.leaseDuration + '时'
                }
                if (data.leaseDurationType == 2) {
                    return data.leaseDuration + '天'
                }

            },
            dizhi: function (data) {
                var s = data.substring(0, 5)
                var b = data.slice(-5)
                return s + '...' + b

            },
            zhuangtai: function (data) {
                if (data.payStatus == 1) {
                    return '待支付'
                }
                if (data.payStatus == 3) {
                    return '支付失败'
                }
                if (data.payStatus == 4) {
                    return '已退款'
                }
                if (data.leaseStatus == 1) {
                    return '未处理'
                }
                if (data.leaseStatus == 2) {
                    return '安排中'
                }
                if (data.leaseStatus == 3) {
                    return '租用成功'
                }
                if (data.leaseStatus == 4) {
                    return '租用失败'
                }


            },
        }
    }
</script>
<style scoped>
    .gongyong {
        position: fixed;
        width: 100%;
        left: 0;
        top: 0;
        z-index: 100000;
    }

    .y1 {
        width: 94%;
        margin: 50px auto;
        background: #fff;
        height: 100px;
        display: flex;
        justify-content: flex-start;
        align-items: center;
        border-radius: 15px;

    }

    .yu1 {
        font-size: 20px;
        font-weight: 550;
    }

    .yu2 {
        font-size: 30px;
        font-weight: 550;
    }


    .y2 {
        padding: 0 20px;
    }

    .e26 {
        font-size: 12px;
        color: #646566;
        padding: 5px 0;
    }

    .e27 {
        text-align: center;
        font-size: 18px;
        padding: 20px 0;
    }

    .e28 {
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 20px 0;
    }

    .e29 {
        text-align: center;
        color: #f3455a;
        padding: 10px 0;

    }

    @media screen and (max-width: 699px) {
        .y1 {
            width: 94%;
            margin: 10px auto;
            background: #fff;
            height: 100px;
            display: flex;
            justify-content: flex-start;
            align-items: center;
            border-radius: 10px;

        }

        .d1 {
            display: none;
            width: 94%;
            margin: 50px auto;
        }

        .yu1 {
            font-size: 16px;
            font-weight: 550;
        }

        .yu2 {
            font-size: 20px;
            font-weight: 550;
        }

        .d2 {
            display: block;

        }

        .d3 {
            background: #fff;
            width: 94%;
            margin: 2% auto;
            border-radius: 10px;
            padding: 15px;

        }

        .d4 {
            display: flex;
            justify-content: space-between;
            align-items: center;

        }

        .d5 {
            width: 80px;
            font-size: 14px;
            color: #1787ff;
            padding: 5px 0;
        }

        .d6 {
            flex: 1;
            width: 0;
            text-align: right;
            font-size: 13px;
            padding: 5px 0;
        }

        .d7 {

            padding: 30px 0;
            display: flex;
            justify-content: flex-end;
            align-items: center;
        }




    }

    /* 平板  768px-992px  屏幕宽度在768px以上显示的颜色*/
    @media screen and (min-width:700px) and (max-width:1200px) {
        .d1 {
            display: block;
            width: 94%;
            margin: 50px auto;
        }

        .d2 {
            display: none;

        }

        .d7 {

            padding: 30px 0;
            display: flex;
            justify-content: flex-end;
            align-items: center;
        }

    }



    @media screen and (min-width:1201px) {
        .d1 {
            display: block;
            width: 94%;
            margin: 50px auto;
        }

        .d2 {
            display: none;

        }

        .d7 {

            padding: 30px 0;
            display: flex;
            justify-content: flex-end;
            align-items: center;
        }




    }
</style>