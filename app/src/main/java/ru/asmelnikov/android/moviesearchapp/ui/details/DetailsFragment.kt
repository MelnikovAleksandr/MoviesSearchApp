package ru.asmelnikov.android.moviesearchapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.asmelnikov.android.moviesearchapp.MovieViewModel
import ru.asmelnikov.android.moviesearchapp.databinding.FragmentDetailsBinding
import ru.asmelnikov.android.moviesearchapp.utils.Status

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: MovieViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.backPress.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.getMovieDetails(args.imdbId!!)
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            when (it.getContentIfNotHandled()?.status) {
                Status.SUCCESS -> {
                    binding.detailsProgress.visibility = View.GONE
                    binding.movieDetails = it.peekContent().data
                }
                Status.LOADING -> {
                    binding.detailsProgress.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.detailsProgress.visibility = View.GONE
                }
                else -> {}
            }
        }
    }
}