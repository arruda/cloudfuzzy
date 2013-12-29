from django.conf.urls import patterns, include, url
from django.views.generic import TemplateView
from api import SystemResource

from django.contrib import admin
admin.autodiscover()

system_resource = SystemResource()

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'cloudfuzzy.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^$', TemplateView.as_view(template_name="index.html"), name='index'),

    (r'^api/', include(system_resource.urls)),

    url(r'^admin/', include(admin.site.urls)),
)
