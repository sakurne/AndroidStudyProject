package com.example.myapplication.presentation.view.event

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.myapplication.data.repositories.AppRepository
import com.example.myapplication.databinding.ActivityEventDetailBinding
import com.example.myapplication.di.AppModule
import com.example.myapplication.di.DaggerRepositoryComponent
import com.example.myapplication.di.DatabaseModule
import com.example.myapplication.domain.event.Event
import com.example.myapplication.presentation.presenter.EventDetailPresenter
import com.example.myapplication.presentation.view.HelpMoneyDialog
import com.example.myapplication.presentation.view.MainActivity
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class EventDetailActivity : MvpAppCompatActivity(), EventDetailView {
    private lateinit var binding: ActivityEventDetailBinding

    @Inject
    lateinit var repository: AppRepository

    @InjectPresenter
    lateinit var presenter: EventDetailPresenter

    companion object {
        const val DATE_PATTERN = "yyyy.MM.dd"
        const val DATE_INFO_FORMAT = "%s - %s"
        const val EVENT_KEY = "event"
        const val HELP_MONEY_DIALOG_TAG = "help_money_dialog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerRepositoryComponent.builder()
            .appModule(AppModule(application))
            .databaseModule(DatabaseModule()).build()
            .inject(this)

        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventId: Long = intent.getLongExtra(EVENT_KEY, 0)
        presenter.showEventInfo(repository, eventId)
    }

    private fun setPreferencesCounter(event: Event) {
        val settings = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE)
        val readMessages = settings.getStringSet(MainActivity.READ_EVENTS_IDS, null)
        val newReadMessages: Set<String>
        if (readMessages != null) {
            newReadMessages = HashSet(readMessages)
            newReadMessages.add(event.id.toString())
        } else {
            newReadMessages = setOf(event.id.toString())
        }
        val editor = settings.edit()
        editor.putStringSet(MainActivity.READ_EVENTS_IDS, newReadMessages)
        editor.apply()
    }

    override fun showEventInfo(event: Event) {
        val formatter = DateTimeFormatter.ofPattern(DATE_PATTERN)
        val dateInfo = DATE_INFO_FORMAT.format(
            event.startDate.format(formatter),
            event.endDate.format(formatter)
        )

        binding.eventNameTextView.text = event.name
        binding.eventDateTextView.text = dateInfo
        binding.eventOrganizationTextView.text = event.organisation
        binding.eventAddressTextView.text = event.address
        binding.eventPhoneTextView.text = event.phone
        binding.eventDescriptionTextView.text = event.description

        val photos = event.photos
        if (photos.size > 0) {
            binding.eventImageView1.setImageResource(
                resources.getIdentifier(
                    photos[0] as String?,
                    "drawable",
                    packageName
                )
            )
            if (photos.size > 1) {
                binding.eventImageView2.setImageResource(
                    resources.getIdentifier(
                        photos[1] as String?,
                        "drawable",
                        packageName
                    )
                )
            } else {
                binding.eventImageView2.visibility = View.GONE
            }
            if (photos.size > 2) {
                binding.eventImageView2.setImageResource(
                    resources.getIdentifier(
                        photos[2] as String?,
                        "drawable",
                        packageName
                    )
                )
            } else {
                binding.eventImageView3.visibility = View.GONE
            }
        }
        setPreferencesCounter(event)
        binding.helpMoneyConstraint.setOnClickListener {
            val dialog = HelpMoneyDialog(event)
            dialog.show(supportFragmentManager, HELP_MONEY_DIALOG_TAG)
        }
        setResult(RESULT_OK, intent)
    }
}
