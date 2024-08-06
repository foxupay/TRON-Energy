<template>
  <div class="index">
   	<div class="errorpage">
   		<img src="../assets/img/404.png"  />
   		<p>老板说了，不让你进这个页面</p>
   	</div>
  </div>
</template>
<script>
import qs from "qs"
export default {
  data () {
    return {
      
    }
  },
  methods:{
   	
  },
  created:function(){
  	
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.errorpage{
	width: 400px;
	margin: auto;
	padding-top: 200px;
}	
.errorpage img{
	width: 100%;
	height: auto;
}
.errorpage p{
	text-align: center;
	color: #35909F;
	font-weight: bold;
	font-size: 18px;
	padding-top: 40px;
}
</style>
