import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

export const store=new Vuex.Store({
	state:{
		data:100,
		user:null,
		fetchMIMCTokenInfo:null,
    	gnSp:{},
		loginTrue: localStorage.getItem("loginTrue") != null ? true : false,
		logindenglu: false
	},
	getters:{
		test:(state)=>{
			return state.data-1
		}
	},
	mutations:{
		getUser:(state,payload)=>{
			state.user=payload.user
			state.fetchMIMCTokenInfo=payload.fetchMIMCTokenInfo
		},
		setGnSp:(state,payload)=>{
			state.gnSp=payload
		},
		loginTrueFc:(state,payload)=>{
			state.loginTrue=payload
		},
		logindengluFc: (state, payload) => {
			state.logindenglu = payload
		}
	},
	actions:{
		getUser:(context,payload)=>{
			context.commit('getUser',payload)
		},
		setGnSp:(context,payload)=>{
			context.commit('setGnSp',payload)
		}
	}
})


//this.$store.state.commit(函数,参数) //执行mutations
