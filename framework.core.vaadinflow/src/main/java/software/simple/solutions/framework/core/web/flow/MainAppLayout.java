package software.simple.solutions.framework.core.web.flow;

import com.github.appreciated.app.layout.notification.DefaultNotificationHolder;
import com.github.appreciated.app.layout.router.AppLayoutRouterLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.Route;

@Push
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@Route("secured")
public class MainAppLayout extends AppLayoutRouterLayout {

	private DefaultNotificationHolder notifications;

	// @Override
	// public AppLayout createAppLayoutInstance() {
	// notifications = new DefaultNotificationHolder(newStatus -> {
	// });
	// // Icon menuBtn = VaadinIcon.MENU.create();
	// AppLayout build =
	// AppLayoutBuilder.get(Behaviour.LEFT_HYBRID).withTitle("App Layout")
	// // .withIconComponent(menuBtn)
	// .withAppBar(
	// AppBarBuilder.get().add(new AppBarNotificationButton(VaadinIcon.BELL,
	// notifications)).build())
	// .withAppMenu(
	// LeftAppMenuBuilder.get()
	// // .addToSection(new RouterLink("ViewA",
	// // ViewA.class), HEADER)
	// .addToSection(new MenuHeaderComponent("Menu-Header", "Version 2.0.6",
	// "/frontend/images/logo.png"), HEADER)
	// .addToSection(new LeftClickableComponent("Clickable Entry",
	// VaadinIcon.COG.create(),
	// clickEvent -> Notification.show("onClick ...")), HEADER)
	// .add(new LeftNavigationComponent("ViewA", VaadinIcon.COG.create(),
	// ViewA.class))
	// // .add(LeftSubMenuBuilder.get("My Submenu",
	// // VaadinIcon.PLUS.create())
	// // .add(LeftSubMenuBuilder.get("My Submenu",
	// // VaadinIcon.PLUS.create())
	// // .add(new
	// // LeftNavigationComponent(View2.class))
	// // .add(new
	// // LeftNavigationComponent(View3.class))
	// // .add(new
	// // LeftNavigationComponent(View4.class)).build())
	// // .add(new
	// // LeftNavigationComponent(View3.class))
	// // .add(new
	// // LeftNavigationComponent(View4.class)).build())
	// // .add(new
	// // LeftNavigationComponent(View5.class))
	// .addToSection(new LeftClickableComponent("Clickable Entry",
	// VaadinIcon.COG.create(),
	// new ComponentEventListener<ClickEvent<?>>() {
	//
	// @Override
	// public void onComponentEvent(ClickEvent<?> event) {
	// closeDrawer();
	//
	// }
	// }), FOOTER)
	// .build())
	// .build();
	//
	// // menuBtn.addClickListener(new
	// // ComponentEventListener<ClickEvent<Icon>>() {
	// //
	// // @Override
	// // public void onComponentEvent(ClickEvent<Icon> event) {
	// // }
	// // });
	//
	// return build;
	// }

}
