package com.mml.onceapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mml.onceapplication.R
import com.mml.onceapplication.databinding.CardItemBinding
import com.mml.onceapplication.databinding.FragmentViewBindBinding

fun CardItemBinding.bind(imageResId: Int, nameStr: String, descStr: String){
    avatar.setImageResource(imageResId)
    name.text = nameStr
    des.text = descStr
}

/**
 * A simple [Fragment] subclass.
 * Use the [ViewBindFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewBindFragment : Fragment(R.layout.fragment_view_bind) {
    private lateinit var binding: FragmentViewBindBinding
    val list = mutableListOf<String>().apply {
        repeat(10){
            add(it.toChar().toString())
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewBindBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      /*  binding.topCard.apply {
            avatar.setImageResource(R.mipmap.ic_launcher)
            name.text="技术最TOP"
            des.text = "扒最前沿科技动态，聊最TOP编程技术。"
        }*/
        binding.topCard.bind(R.mipmap.ic_launcher,"技术最TOP","扒最前沿科技动态")
        binding.recyclerView.apply {
            adapter = object:RecyclerView.Adapter<CardItemViewHolder>(){
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): CardItemViewHolder {
                    val cardItemBinding = CardItemBinding.inflate(layoutInflater,parent,false)
                    return CardItemViewHolder(cardItemBinding)
                }

                override fun getItemCount(): Int {
                    return list.size
                }

                override fun onBindViewHolder(holder: CardItemViewHolder, position: Int) {
                    holder.cardItemBinding.bind(R.mipmap.ic_launcher,"技术最TOP$position","扒最前沿科技动态")
                }

            }
        }
    }
    class  CardItemViewHolder(var cardItemBinding:CardItemBinding): RecyclerView.ViewHolder(cardItemBinding.root) {
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            ViewBindFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}