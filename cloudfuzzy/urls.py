from django.conf.urls import patterns, include, url
from django.conf import settings
from django.views.generic import TemplateView

from django.contrib import admin
admin.autodiscover()


urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'cloudfuzzy.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^$', TemplateView.as_view(template_name="index.html"), name='index'),
    url(r'^teste/$', TemplateView.as_view(template_name="teste.html"), name='index'),
    url(r'^systems/$', TemplateView.as_view(template_name="systems/list.html"), name='systems'),

    (r'^api/', include('api_rest.urls')),

    url(r'^admin/', include(admin.site.urls)),
)

urlpatterns += patterns('django.contrib.auth.views',
    url(r'^login/$', 'login', {'template_name': 'users/login.html', }, name='login'),
    url(r'^logout/$', 'logout', {'template_name': 'users/login.html'}, name='logout'),
)


if settings.DEBUG:
    import debug_toolbar
    urlpatterns += patterns('',
        url(r'^__debug__/', include(debug_toolbar.urls)),
    )
