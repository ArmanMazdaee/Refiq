package com.eth.refiq.ui.wallet.importwallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eth.refiq.R
import com.eth.refiq.databinding.FragmentImportWalletBinding
import com.eth.refiq.ui.wallet.WalletViewModel
import com.eth.refiq.ui.wallet.password.EnterPasswordFragment
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ImportWalletFragment : Fragment() {
    private var _binding: FragmentImportWalletBinding? = null

    private val walletViewModel: WalletViewModel by activityViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImportWalletBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val binding get() = _binding!!
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val password = requireArguments().getString(EnterPasswordFragment.PASSWORD)!!

        walletViewModel.walletImported.observe(viewLifecycleOwner) {
            if (it){
                walletViewModel.saveWallet()
                findNavController().popBackStack(R.id.navigation_wallet, true)
            }
        }
        binding.importwalletDone.setOnClickListener {

            walletViewModel.importWallet(
                binding.importwalletEdittextMnemonic.text.toString(),
                password
            )
        }
    }
}