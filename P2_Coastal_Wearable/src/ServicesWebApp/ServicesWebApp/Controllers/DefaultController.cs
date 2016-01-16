using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Web;
using System.Web.Mvc;

namespace ServicesWebApp.Controllers
{
    public class DefaultController : Controller
    {
        [AllowAnonymous, Route("get-nearest-bathing-water/{easting}/{northing}")]
        public ActionResult getNearestBathingWater(int easting, int northing)
        {
            var url = "http://environment.data.gov.uk/doc/nearest-bathing-water/";

            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri(url);

            client.DefaultRequestHeaders.Accept.Add(
            new MediaTypeWithQualityHeaderValue("application/json"));

            HttpResponseMessage response = client.GetAsync(string.Format("easting/{0}/northing/{1}.json", easting, northing)).Result;
            if (response.IsSuccessStatusCode)
            {
                var data = response.Content.ReadAsStringAsync().Result;

                dynamic json = JObject.Parse(data);

                return Json(((JArray)json.result.items).Select(s =>
                new
                {
                    Name = s.Value<JObject>("name").Value<string>("_value"),
                    Quality = s.Value<JObject>("latestComplianceAssessment").Value<JObject>("complianceClassification").Value<JObject>("name").Value<string>("_value")
                }), JsonRequestBehavior.AllowGet);
            }

            return Json(true);
        }

        [AllowAnonymous, Route("get-tide-table/{easting}/{northing}")]
        public ActionResult getTideTable(int easting, int northing)
        {

            return Json(true);
        }
    }
}