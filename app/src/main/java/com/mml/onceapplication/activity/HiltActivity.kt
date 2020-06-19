package com.mml.onceapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mml.onceapplication.R
import com.mml.onceapplication.databinding.ActivityHiltBinding
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Inject
import javax.inject.Qualifier

class Item @Inject constructor(){
    val hash = "sss"
}
class HiltViewModel @ViewModelInject constructor(
    var item: Item,
    @Assisted private val savedState: SavedStateHandle
): ViewModel() {

}
@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {
    @ActivityHash
    @Provides
    fun provideHash(): String {
        return hashCode().toString()
    }
}
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class ActivityHash

@AndroidEntryPoint
class HiltActivity : AppCompatActivity() {
    @JvmField
    @ActivityHash
    @Inject
    var hash: String? = null
    @ActivityHash
    @Inject
    lateinit var hash1: String
    @Inject
    lateinit var item: Item
    private val viewModel by viewModels<HiltViewModel>()
    private lateinit var binding: ActivityHiltBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityHiltBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvText.text="$hash    $hash1   ${viewModel.item.hash}  ${item.hash}"
    }
}