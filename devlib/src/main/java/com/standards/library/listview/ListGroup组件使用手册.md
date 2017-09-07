

### 使用之前需要做几项配置：

1.需要修改List返回的实体类ListData
	根据不同项目的定义的不同接口，修改相应的实体类，注意，这里修改，
	可能修改修改BaseGroupListManager manager的加载方式, 故，需要看得懂BaseGroupListManager的加载逻辑
	才能更好的拓展。

2.需要配置Group组件的基本返回码
	建议在Application启动的时候配置:
	例：
	
	public class App extends Application {

		@Override
		public void onCreate() {
			super.onCreate();
			
			int successCode = 1000;
			int emptyCode = 1004;
			ReturnCodeConfig.getInstance().initReturnCode(successCode, emptyCode);
		}
	}
	
	
3.使用方法
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PullToRefreshListViewImpl<Item> groupListView = new PullToRefreshListViewImpl<Item>(false, false);
		JokeManager groupManager = new JokeManager();
		JokeAdapter groupAdapter = new JokeAdapter(this);
		
		// 1.使用Dialog的加载样式
		BaseLoadingDialogHelp loadingHelp = new BaseLoadingDialogHelp(this);
		// 2.使用Loading页的加载样式
		BaseLoadingPageHelp loadingHelp = new BaseLoadingPageHelp(context);

		IGroupPresenter group = listListGroupFactory.create(this, groupListView, groupManager, groupAdapter, loadingHelp);

		View contentView = group.getRootView();
		setContentView(contentView);
	}


### 说明

	这个组件库分为四大模块：
	1. IGroupListView 列表控件				(PullToRefreshListViewImpl 下拉上拉刷新组件)
	2. IGroupManager 加载数据的Manager		(BaseGroupListManager manager在list列表当中的一个实现类)
	3. IGroupAdapter 列表适配器				(ListView的适配器)
	4. IGroupLoadingHelp 加载样式，			(BaseLoadingDialogHelp、BaseLoadingPageHelp 包括loading页和错误loading页)	
	
	这些组件四大模块都是基于接口开发，在使用到具体项目时，需要重新拓展，可以实现这些接口进行开发
	
	本组件对拓展开放，对修改关闭



