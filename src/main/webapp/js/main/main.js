var urls = {
	"data_source":"main/datasource/datasource.jsp",
	"data_import":"main/dataImport/hdfs_show.jsp",
	"data_analyze":"analysis/analysisMR.jsp",
	"flow_manage":"flowManage/flowManage.html",
	"data_show":"main/resultShow/testIndex.html",
}
jQuery(function(){
	//左侧导航栏点击
	jQuery(".left>a").click(function(){
		var id = jQuery(this).attr("id");
		for(var key in urls){
			if(id === key){
				jQuery(".left>a").removeClass("current");
				jQuery(this).addClass("curgit rent");
			}
		}

		jQuery(".right>iframe").attr("src",urls[id]);

	})
})
