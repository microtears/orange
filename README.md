# Kotlin Android Util

## 本库主要包括：

- 日常使用中使用到的Kotlin函数

    示例：

    未添加。
- Retrofit2配合LiveData需要的`LiveDataCallAdapterFactory`
- 可以与Kotlin协程库配合的CoroutineViewModel
- 基于Kotlin扩展方法的LiveData响应式流操作符（拥有Java静态方法版本），包括但不限于`map`,`mapIndex`,`merge`,`flatMap`,`filter`,`distinct`,`throttleFirst`,`collect`,`reduce`,`count`,`cast`,`groupBy`,`timestamp`,`take`,`takeWhile`,`skip`,`skipUntil`,`at`

    示例1：

    ```kotlin
    private var isAdd = false
    fun tasks(): MediatorLiveData<List<MutableLiveData<DownloadTask>>> {
        if (!isAdd) {
            val paused = downloadTaskService.getPausedTasks().map { list ->
                list.map {
                    MutableLiveData(it)
                }
            }
            val finished = downloadTaskService.getFinishedTasks().map { list ->
                list.map {
                    MutableLiveData(it)
                }
            }
            val combine = listOf(downloadService.tasks(), paused, finished).mergeList()
            tasks.addSource(combine) {
                tasks.value = it.sortedByDescending { item ->
                    item.value?.createTime?.toDate() ?: Date()
                }
            }
        }
        return tasks
    }
    ```

    示例2：

    ```kotlin
    class WallpaperModel(application: Application) : CoroutineViewModel(application) {

        data class Extra(var skip: Int = 0, var isFirstAdd: Boolean = true)

        private val wallpaperCache = HashMap<String, Any>()
        private val extraCache = HashMap<String, Any>()
        private val wallpaperRepo = Wallpaper2Repository
        val categoryIndexs = SparseIntArray(4)

        var channelIndex = AppSetting.initChannel

        fun getAllChannel() = wallpaperRepo.getAllChannel()

        fun getCategory(channel: Int, isFilter: Boolean = true): LiveData<List<Category>> {
            val result = wallpaperRepo.getCategory(channel)
            return if (!isFilter) result
            else {
                val set = AppSetting.ignores
                result.map { list ->
                    list.filter { !set.contains(it.key) }
                }
            }
        }

        fun getAllCategory(isFilter: Boolean = false): LiveData<List<Category>> {
            return if (!isFilter) wallpaperRepo.getAllCategory()
            else {
                val set = AppSetting.ignores
                wallpaperRepo.getAllCategory().map { list ->
                    list.filter {
                        !set.contains(it.key).apply {
                            wallpaperCache.remove(it.key)
                        }
                    }
                }
            }
        }

        fun getWallpaper(category: Category, skip: Int = 0): LiveData<List<Wallpaper>> {
            val key = category.key
            val extra = getCache(extraCache, key) { Extra() }
            extra.skip = skip
            val mediatorLiveData = getCache(wallpaperCache, key) { MediatorLiveData<List<Wallpaper>>() }
            if (extra.isFirstAdd || skip != 0) {
                extra.isFirstAdd = false
                val data = wallpaperRepo.getWallpaper(category, skip)
                mediatorLiveData.addSource(data) { list ->
                    val newList = mutableListOf<Wallpaper>()
                    newList.addAll(mediatorLiveData.value ?: emptyList())
                    newList.addAll(list)
                    mediatorLiveData.value = newList
                    mediatorLiveData.removeSource(data)
                }
            }
            return mediatorLiveData
        }


        fun skip(category: Category, skip: Int = (getCache(extraCache, category.key) { Extra() }).skip + 20)     {
            getWallpaper(category, skip)
        }

    }
    ```

- 一个适用于Glide Transition的`ColorMatrixTransition`

    示例：

    ```kotlin
    val transitionOptions = ColorMatrixTransitionOptions.withSaturationFade()

    fun onBindViewHolder(holder: ViewHolder, item: Wallpaper) {
        super.onBindViewHolder(holder, item)
        holder.load(
            R.id.itemImageView, item.showImage,
            translation = transitionOptions,
            notAnimate = true
        )
    }

    fun ImageView.load(
        obj: Any?,
        option: RequestOptions? = null,
        listener: RequestListener<Drawable>? = null,
        translation: TransitionOptions<*, in Drawable>? = null,
        thumbnail: RequestBuilder<Drawable>? = null,
        override: Pair<Int, Int>? = null,
        notAnimate: Boolean = false
    ) {
        val builder = GlideApp.with(this).load(obj)
        if (option != null)
            builder.apply(option)
        if (listener != null)
            builder.listener(listener)
        if (translation != null)
            builder.transition(translation)
        if (thumbnail != null)
            builder.thumbnail(thumbnail)
        if (override != null)
            builder.override(override.first, override.second)
        if (notAnimate)
            builder.dontAnimate()

        builder
            .skipMemoryCache(false)
            .into(this)
    }

    fun ImageView.clear() {
        GlideApp.with(this).clear(this)
    }

    fun ViewHolder.load(
        @IdRes id: Int, obj: Any?,
        option: RequestOptions? = null,
        listener: RequestListener<Drawable>? = null,
        translation: TransitionOptions<*, in Drawable>? = null,
        thumbnail: RequestBuilder<Drawable>? = null,
        override: Pair<Int, Int>? = null,
        notAnimate: Boolean = false
    ) {
        view<ImageView>(id).apply {
            clear()
            load(obj, option, listener, translation, thumbnail, override, notAnimate)
        }
    }
    ```

- 利用Kotlin lambda参数简化Android权限申请的`PermissionFragment`

    示例：

    ```kotlin
    requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE) { result ->
        if (result) {
            viewModel.save(wallpaper.largeImageURL, "${wallpaper.id}.jpg", listener = downloadListener)
            toast2(getString(R.string.notice_add_download_task))
        } else {
            toast2(getString(R.string.notice_require_permission))
        }
    }
    ```

- 利用Kotlin lambda参数简化Android Activity返回值的`ResultFragment`

    示例：

    ```kotlin
    startActivityForResult(Intent(context, IgnoreLabelsActivity::class.java)) { resultCode: Int, data:  Intent? ->
        activity?.takeIf { resultCode == Activity.RESULT_OK }?.let {
            //todo fix refresh
            val FIXED_REFRESH = false
            if (FIXED_REFRESH)
                ActivityCompat.recreate(it)
            else {
                it.finish()
                startActivity(Intent(activity, MainActivity2::class.java))
            }
        }
    }
    ```

- 一个参照自Kotlin官方的Preference代理类

    示例：

    ```kotlin
    object Setting {
        var blurPixels: Int by Preference(200)
        var isCustom: Boolean by Preference(false)
        var imagePath: String by Preference("")
        var downloadTask: Long by Preference(0L)
        var darkTheme: Boolean by Preference(false)
    }
    ```

- Preference代理类的扩展类PreferenceX

    示例：

    ```kotlin
    object Setting {
        var enableAnim: Boolean by PreferenceX(true, null, { _: Any?, _: KProperty<*>, value: Boolean ->
            animLiveData.value = value
        })
        val animLiveData: MutableLiveData<Boolean> = MutableLiveData(enableAnim)
    }
    ```

- 一个尚未发布的RecyclerView Adapter类：`MultiTypeAdapter`。利用Kotlin lambda参数简化Adapter的编写，避免为每一个RecyclerView都要生成一个Adapter Class的麻烦，最大程度复用代码，同时提供多类型支持，预加载支持。

    示例1：

    ```kotlin
            recyclerView.apply {
                val spanCount = if (isPortrait) 2 else 3
                layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
                addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.split_small),     spanCount))
                adapter = adapterOf<Favorite> { holder, item ->
                    val position = holder.layoutPosition
                    val height = heightCache.get(position, resources.getDimensionPixelSize  (R.dimen.image_height_normal))
                    holder.load(
                        R.id.itemImageView, item.previewURL,
                        listener = Listener(position, heightCache),
                        override = widthCache to height,
                        notAnimate = true
                    )
                    holder.transitionName(R.id.itemImageView, item.showImage)
                    /*
                      * like bind
                      * */
                    holder.imageResource(R.id.likeButton, R.drawable.ic_favorite_border_white_24dp)
                    viewModel.isLiked(item.showImage).observe(this@FavoriteFragment, Observer { liked ->
                        if (liked) holder.imageResource(R.id.likeButton,    R.drawable.ic_favorite_red_400_24dp)
                        else holder.imageResource(R.id.likeButton, R.drawable.ic_favorite_border_white_24dp)
                    })
                    lateinit var observer: Observer<Boolean>
                    observer = Observer { liked ->
                        if (liked) {
                            Snackbar.make(view, "确定要取消收藏吗", Snackbar.LENGTH_LONG)
                                .also {
                                    it.view.setBackgroundColor(Color.WHITE)
                                    it.view.findViewById<TextView>  (com.google.android.material.R.id.snackbar_text)
                                        .setTextColor(Color.BLACK)
                                }
                                .setAction("确定") {
                                    viewModel.unlike(item.showImage)
                                }
                                .show()
                        } else
                            viewModel.like(item)
                        viewModel.isLiked(item.showImage).removeObserver(observer)
                    }
                    holder.onClick(R.id.likeButton) {
                        viewModel.isLiked(item.showImage).observe(this@FavoriteFragment, observer)
                    }
                    holder.onClick(R.id.downloadButton) {
                        requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE) { result ->
                            if (result) {
                                downloadActionModel.save(item.largeImageURL, "${item.id}.jpg", listener =   downloadListener)
                                toast2(getString(R.string.notice_add_download_task))
                            } else {
                                toast2(getString(R.string.notice_require_permission))
                            }
                        }
                    }
                }.apply {
                    onItemClick = { view, position ->
                        val transitionView = view.findViewById<View>(R.id.itemImageView)!!
                        val bindRecyclerView = view.parent as? RecyclerView
                        bindRecyclerView?.let { recyclerView ->
                            AppSetting.sharedElementEnterPosition = position
                            AppSetting.sharedElementExitPosition = position
                            AppSetting.sharedElementMinPosition = recyclerView.getFirstItemPosition()
                            AppSetting.sharedElementMaxPosition = recyclerView.getLastItemPosition()
                            (recyclerView.adapter as? MultiTypeAdapter)?.data?.let { data ->
                                activity?.let {
                                    val mapData = ArrayList<Wallpaper>(data.map {
                                        val favorite = it.first as Favorite
                                        Wallpaper(
                                            id = favorite.id, category = favorite.category, channel =   favorite.channel,
                                            previewURL = favorite.previewURL, middleImageURL =  favorite.middleImageURL,
                                            largeImageURL = favorite.largeImageURL
                                        )
                                    })
                                    ImagePreviewActivity.navigate(it, mapData, position, transitionView,    transitionView.transitionName ?: "")
                                }
                            }
                        }
                    }
                }
            }
    ```

    示例2：

    ```kotlin
        private fun getAdapter(liveData: LiveData<List<Wallpaper>>): MultiTypeAdapter {
            if (this.adapter != null) return this.adapter!!
            val adapter = adapterOf(mutableListOf(), this::onBindViewHolder).apply {
                onItemClick = { view: View, itemPosition: Int ->
                    val transitionView = view.findViewById<View>(R.id.itemImageView)!!
                    val bindRecyclerView = view.parent as? RecyclerView
                    bindRecyclerView?.let { recyclerView ->
                        AppSetting.sharedElementEnterPosition = itemPosition
                        AppSetting.sharedElementExitPosition = itemPosition
                        AppSetting.sharedElementMinPosition = recyclerView.getFirstItemPosition()
                        AppSetting.sharedElementMaxPosition = recyclerView.getLastItemPosition()
                        (recyclerView.adapter as? MultiTypeAdapter)?.data?.let { data ->
                            activity?.let {
                                val mapData = ArrayList<Wallpaper>(data.map { it.first as Wallpaper })
                                ImagePreviewActivity.navigate(it, mapData, itemPosition, transitionView,    transitionView.transitionName ?: "")
                            }
                        }
                    }
                }
                onPreload = {
                    category?.let { viewModel.skip(it) }
                }
            }
            liveData.observe(this, Observer { newData ->
                adapter.replaceAll(newData.map { it to itemLayoutId })
                loadingPage.removeInParent()
            })
            this.adapter = adapter
            return adapter
        }
    ```
