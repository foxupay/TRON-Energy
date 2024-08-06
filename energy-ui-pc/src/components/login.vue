<template>
	<div id="ass">

		<div style="margin: 0 auto;">
			<div style="width:300px ;" v-loading="loading">
				<div style="padding: 20px 0; text-align: center;">
					<img class="img" src="../assets/img/logo.png" alt="">
				</div>
				<div v-if="!active">
					<el-form :model="ruleForm" :rules="rules" ref="ruleForm">
						<el-form-item prop="account" class="listinput">
							<el-input v-model="ruleForm.account" placeholder="请输入邮箱"></el-input>
						</el-form-item>

						<el-form-item prop="code" class="listinput">
							<el-input v-model="ruleForm.code" placeholder="请输入验证码"></el-input>
							<div class="code" @click="timing" v-if="getYZM">获取验证码</div>
							<div class="code" v-if="!getYZM">{{numb}}s</div>
						</el-form-item>
					</el-form>
				</div>
				<div v-if="active">
					<el-form :model="ruleForm" :rules="rules" ref="ruleForm">
						<el-form-item prop="account" class="listinput">
							<el-input v-model="ruleForm.account" placeholder="请输入邮箱"></el-input>
						</el-form-item>

						<el-form-item prop="password" class="listinput">
							<el-input type="password" v-model="ruleForm.password" placeholder="请输入密码"></el-input>
						</el-form-item>
					</el-form>
				</div>

				<el-button style="width: 100%;margin-top: 10px; padding: 10px 0;" type="primary"
					@click="submitForm('ruleForm')" @keyup.enter="enterSearch()">确定
				</el-button>

				<div v-if="!active"
					style="font-size:14px ;color: #1989fa;text-align: center;margin-top: 30px;cursor: pointer;"
					@click="qiehuan">
					密码登录
				</div>
				<div v-if="active"
					style="font-size:14px ;color: #1989fa;text-align: center;margin-top: 30px;cursor: pointer;"
					@click="qiehuan">
					验证码登录
				</div>

			</div>

		</div>





	</div>
</template>
<script>
	import JSEncrypt from 'jsencrypt'
	import qs from "qs"
	export default {
		data() {
			return {
				loading: false,

				ruleForm: {
					account: '',
					code: '',
					password: ''
				},
				rules: {

					account: [
						{ required: true, pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$/, message: '请输入邮箱', trigger: 'change' }
					],
					code: [
						{ required: true, message: '请输入验证码', trigger: 'change' }
					],
					password: [
						{ required: true, message: '请输入密码', trigger: 'change' }
					]
				},
				getYZM: true,
				numb: 60,
				active: false,

			}
		},

		methods: {
			tiaokuan() {
				window.open('https://www.foxupay.com/agreement/energy.html')
			},
			qiehuan() {
				this.active = !this.active
			},
			timing() {
				//验证邮箱
				var myreg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$/;
				if (!myreg.test(this.ruleForm.account)) {

					this.$message.error("请输入正确的邮箱");
					return false
				}
				this.getYZM = false
				var clearTime = setInterval(time => {
					this.numb = this.numb - 1;
					if (this.numb <= 0) {
						clearInterval(clearTime);
						this.numb = 60;
						this.getYZM = true;
					}

				}, 1000);
				this.sendCode()
			},
			//获取验证码
			sendCode() {
				var params = {
					'account': this.ruleForm.account,
				}
				this.$axios({
					method: 'GET',
					url: '/api/app/user/login/code',
					params: params,
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded',
					}
				}).then(userData => {
					if (userData.data.code == "200") {

						this.$message({
							message: '验证码已发送',
							type: 'success'
						});

					} else {

						this.$message.error(userData.data.msg);
					}
				}).catch(error => {
					console.log(error)
				})

			},

			submitForm(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.loading = false
						this.loginto()
					} else {
						return false;
					}
				});
			},
			//回车搜索
			enterSearch() {
				document.onkeydown = e => {
					//13表示回车键，baseURI是当前页面的地址，为了更严谨，也可以加别的，可以打印e看一下
					if (e.keyCode === 13) {
						//回车后执行搜索方法
						this.submitForm('ruleForm')
					}
				}
			},


			loginto() {
				//验证邮箱
				var myreg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$/;
				if (!myreg.test(this.ruleForm.account)) {

					this.$message.error("请输入正确的邮箱");
					return false
				}
				if (this.active == false) {
					if (this.ruleForm.code == '') {
						this.$message.error("请输入验证码");
						return false
					}
					this.yzmdenglu()
				} else {
					if (this.ruleForm.password == '') {
						this.$message.error("请输入密码");
						return false
					}
					this.mimadenglu()
				}



			},
			yzmdenglu() {
				this.loading = true

				var params = qs.stringify({
					'account': this.ruleForm.account,
					'code': this.ruleForm.code,
				})

				this.$axios.post('/api/app/user/login/code', params, {
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded',

					}
				}).then(userData => {

					if (userData.data.code == '200') {
						this.setcookie('nltoken', userData.data.data.token)
						this.loading = false
						this.$router.replace({ name: 'index' })
					}
					else {
						this.loading = false
						this.$message.error(userData.data.msg);
					}
				}).catch(error => {
					console.log(error)
				})
			},
			mimadenglu() {
				this.loading = true
				var encryptor = new JSEncrypt()
				encryptor.setPublicKey('MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjV+uSsM9XcALPMnxjS1v3rhPLfkehaEx2q1eoX5x8YDizqjAO3bLmmzXIhmQIgu1saBKBN9HPQ3q+izpN7b0MGMPeYLiXVWXpe6z40L1RTSt7Y96nMBqB2RwD6ezimc0d9RWvmB7KpeBzLYS1u5ov8kDGqjWvY+zV6EcPB1lC+oMvhUezAnzBSAtyZqnByHznXIFuL9TkJuPoGOj7wykNk7j4enrnSrOhCv26Wyte7NVvNS3Gqetf4OkPCub+28g9reEi+6DPBOVDja2e8DPcIGQxqLRkA7AaSJPB/B/WzXoMq+MOQ5NJBseRvUg5owgWCcTH3NxLLN/kfu370L7jwIDAQAB') // 设置公钥
				var mimajiami = 'DECRYPT:' + this.ruleForm.password
				var mima = encryptor.encrypt(mimajiami) // 对需要加密的数据进行加密


				var params = qs.stringify({
					'account': this.ruleForm.account,
					'password': mima,
				})

				this.$axios.post('/api/app/user/login/pass', params, {
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded',

					}
				}).then(userData => {

					if (userData.data.code == '200') {
						this.setcookie('nltoken', userData.data.data.token)
						this.loading = false
						this.$router.replace({ name: 'index' })
					}
					else {
						this.loading = false
						this.$message.error(userData.data.msg);
					}
				}).catch(error => {
					console.log(error)
				})
			},
			setcookie(name, value) {
				//设置名称为name,值为value的Cookie
				var expdate = new Date();   //初始化时间
				expdate.setTime(expdate.getTime() + 24 * 7 * 60 * 60 * 1000);   //时间单位毫秒 过期时间30分钟
				document.cookie = name + "=" + escape(value) + ";expires=" + expdate.toGMTString() + ";path=/";
			},
		},
		created: function () {
			this.enterSearch()
		},
		filters: {
			conStatus: function (data) {
				if (data == "0") {
					return "正常"
				}
				if (data == "1") {
					return "冻结"
				}
			}
		}
	}
</script>
<style scoped>
	#ass {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		width: 100%;
		height: 100%;

	}

	.code {
		position: absolute;
		top: 0;
		z-index: 0;
		line-height: 32px;
		right: 10px;
		font-size: 12px;
		color: #646566;
		cursor: pointer;
	}

	.img {
		width: 200px;
		height: 200px;
	}

	@media screen and (max-width: 699px) {
		.img {
			width: 100px;
			height: 100px;
		}
	}

	/* 平板  768px-992px  屏幕宽度在768px以上显示的颜色*/
	@media screen and (min-width:700px) and (max-width:1200px) {}



	@media screen and (min-width:1201px) {}
</style>