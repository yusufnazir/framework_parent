package software.simple.solutions.framework.core.web.flow;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

import de.mekaso.vaadin.addon.compani.AnimatedComponent;
import de.mekaso.vaadin.addon.compani.Animator;
import de.mekaso.vaadin.addon.compani.animation.AnimationBuilder;
import de.mekaso.vaadin.addon.compani.animation.AnimationTypes;
import de.mekaso.vaadin.addon.compani.effect.EntranceEffect;
import de.mekaso.vaadin.addon.compani.effect.Speed;

/**
 * The main view contains a button and a click listener.
 */
@Push
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@Route("login")
@StyleSheet("loginview.css")
public class LoginView extends FlexLayout implements RouterLayout {

	private static final long serialVersionUID = -3279206519129928810L;

	public LoginView() {
		super.addClassName("flex-container");
		super.getElement().getStyle().set("justify-content", "center");
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.addClassName("login-form");
		add(verticalLayout);

		Label welcomeLbl = new Label();
		welcomeLbl.setWidth("100%");
		welcomeLbl.setText("Welcome to!");
		verticalLayout.add(welcomeLbl);

		TextField usernameFld = new TextField();
		usernameFld.setWidth("100%");
		usernameFld.setLabel("Username");
		usernameFld.setPlaceholder("Username");
		verticalLayout.add(usernameFld);

		PasswordField passwordField = new PasswordField();
		passwordField.setWidth("100%");
		passwordField.setLabel("Password");
		passwordField.setPlaceholder("Password");
		verticalLayout.add(passwordField);

		Button submitFld = new Button();
		submitFld.setClassName("login-submit-btn");
		submitFld.setWidth("100%");
		submitFld.setText("Log In");
		verticalLayout.add(submitFld);
		verticalLayout.setHorizontalComponentAlignment(Alignment.CENTER, submitFld);

		Anchor forgotPasswordFld = new Anchor();
		forgotPasswordFld.setText("Forgot Password?");
		verticalLayout.add(forgotPasswordFld);
		verticalLayout.setHorizontalComponentAlignment(Alignment.CENTER, forgotPasswordFld);

		submitFld.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = 1425155324269087097L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				submitFld.getUI().ifPresent(ui -> ui.navigate(MainAppLayout.class));
			}
		});

		final Animator animator = Animator.create();
		add(animator);
		AnimatedComponent animatedComponent = animator.prepareComponent(verticalLayout);
		animatedComponent.registerEntranceAnimation(
				AnimationBuilder.createBuilder().create(AnimationTypes.EntranceAnimation.class)
						.withEffect(EntranceEffect.fadeInUp).withSpeed(Speed.slow));
	}

}