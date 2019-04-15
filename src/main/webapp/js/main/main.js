var urls = {
	"data_source":"main/datasource/datasource.jsp",
	"data_import":"main/dataImport/hdfs_show.jsp",
	"algorithm_manage":"main/algorithmManage/algorithm.jsp",
	"data_analyze":"main/analysis/analysisMR.jsp",
	"flow_manage":"main/flowManage/flowManage.html",
	"data_show":"main/resultShow/testIndex.html",
}
jQuery(function(){
	//左侧导航栏点击
	jQuery(".left>a").click(function(){
		var id = jQuery(this).attr("id");
		for(var key in urls){
			if(id === key){
				jQuery(".left>a").removeClass("current");
				jQuery(this).addClass("current");
			}
		}

		jQuery(".right>iframe").attr("src",urls[id]);

	})

})
