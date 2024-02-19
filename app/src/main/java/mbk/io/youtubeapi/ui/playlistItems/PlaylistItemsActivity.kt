package mbk.io.youtubeapi.ui.playlistItems

import android.os.Bundle
import androidx.core.view.isVisible
import mbk.io.youtubeapi.data.base.BaseActivity
import mbk.io.youtubeapi.databinding.ActivityPlaylistItemsBinding
import mbk.io.youtubeapi.ui.playlistItems.adapter.PlaylistsVideoAdapter
import mbk.io.youtubeapi.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistItemsActivity : BaseActivity() {
    private lateinit var binding: ActivityPlaylistItemsBinding
    private val viewModel: VideoViewModel by viewModel()
    private val adapter by lazy {
        PlaylistsVideoAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaylistItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvPlaylistItems.adapter = adapter

        val getId = intent.getStringExtra("id").toString()
        val getTitle = intent.getStringExtra("title").toString()
        val getDesc = intent.getStringExtra("description").toString()
        val getSize = intent.getIntExtra("size", 0)

        binding.tvTitle.text = getTitle
        binding.tvDescription.text = getDesc

        viewModel.getPlaylistVideo(getId,getSize).stateHandler(
            success = {
                adapter.submitList(it)
            },
            state = { binding.progressBar.isVisible = it is Resource.Loading }
        )
    }
}